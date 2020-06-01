package com.seanutf.cmmonui.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.seanutf.android.commonutil.CommonUtilApp;
import com.seanutf.cmmonui.demo.DensityNoneChangeDemoActivity;

import java.lang.reflect.Method;

/**
 * Created by Sean on 2018/8/19.
 */
public class DensityUtil {

    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    public static float getScreenDensity() {
        return CommonUtilApp.instance.getResources().getDisplayMetrics().density;
    }

    public static float getScreenDensityDpi(Context context) {
        return CommonUtilApp.instance.getResources().getDisplayMetrics().densityDpi;
    }

    public static float getScreenHeight() {
        return CommonUtilApp.instance.getResources().getDisplayMetrics().heightPixels;
    }

    public static float getScreenWidth() {
        return CommonUtilApp.instance.getResources().getDisplayMetrics().widthPixels;
    }

    public static float getScaledDensity() {
        return CommonUtilApp.instance.getResources().getDisplayMetrics().scaledDensity;
    }

    public static int dp2px(float dp) {
        float density = CommonUtilApp.instance.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5F);
    }


    public static int sp2px(float sp) {
        float density = CommonUtilApp.instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5F);
    }

    public static int px2dp(float px) {
        float density = CommonUtilApp.instance.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5F);
    }

    public static int px2sp(float px) {
        float density = CommonUtilApp.instance.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / density + 0.5F);
    }

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        DisplayMetrics applicationDisplayMetrics = application.getResources().getDisplayMetrics();


        if (sNoncompatDensity == 0) {
            sNoncompatDensity = applicationDisplayMetrics.density;
            sNoncompatScaledDensity = applicationDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }


        final float targetDensity = applicationDisplayMetrics.widthPixels / 360.0f;
        final float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        //        final int targetDensityDpi = (int) (160 * targetDensity);


        applicationDisplayMetrics.density = targetDensity;
        applicationDisplayMetrics.scaledDensity = targetScaledDensity;
        //        applicationDisplayMetrics.densityDpi = targetDensityDpi;


        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        //        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }


    /**
     * Converts an unpacked complex data value holding a dimension to its final floating
     * point value. The two parameters <var>unit</var> and <var>value</var>
     * are as in COMPLEX_UNIT_PX  COMPLEX_UNIT_DIP COMPLEX_UNIT_SP COMPLEX_UNIT_PT
     * COMPLEX_UNIT_IN
     * COMPLEX_UNIT_MM
     *
     * @param unit  The unit to convert from.
     * @param value The value to apply the unit to.
     */
    public static float applyDimension(int unit, float value) {
        WindowManager wm = (WindowManager) CommonUtilApp.instance.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            DisplayMetrics metric = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metric);
            return TypedValue.applyDimension(unit, value, metric);
        }
        return value;
    }

    public static Point appDisplaySize() {
        Point point = new Point(0, 0);
        WindowManager wm = (WindowManager) CommonUtilApp.instance.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            wm.getDefaultDisplay().getSize(point);
        }
        return point;
    }


    private static int systemDensityDpi;
    private static float systemFontScale;


    /**
     * 当页面需要禁用字体大小和7.0之后显示大小时，可以配合使用
     * 警告：仅做代码库使用，慎用，较小情况下会导致systemDensityDpi恢复异常，
     * 导致其他页面可能会出现字体忽大忽小的问题，
     * 如有业务场景确实需要禁用字体大小和7.0之后显示大小时，看情况优先推荐使用px代替dp,sp，再尝试本方法
     * 使用参见{@link DensityNoneChangeDemoActivity}
     */
    public static int getDefaultDisplayDensity() {
        try {
            Class<?> aClass = Class.forName("android.view.WindowManagerGlobal");
            Method method = aClass.getMethod("getWindowManagerService");
            method.setAccessible(true);
            Object iwm = method.invoke(aClass);
            Method getInitialDisplayDensity = iwm.getClass().getMethod("getInitialDisplayDensity", int.class);
            getInitialDisplayDensity.setAccessible(true);
            Object densityDpi = getInitialDisplayDensity.invoke(iwm, Display.DEFAULT_DISPLAY);
            return (int) densityDpi;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 恢复使用7.0（23）以上显示大小改变和文字大小
     * 警告：仅做代码库使用，慎用，较小情况下会导致systemDensityDpi恢复异常，
     * 导致其他页面可能会出现字体忽大忽小的问题
     * 如有业务场景确实需要禁用字体大小和7.0之后显示大小时，看情况优先推荐使用px代替dp,sp，再尝试本方法
     * 使用参见{@link DensityNoneChangeDemoActivity}
     */
    public static void restoreSystemDisplayDip(Resources res) {
        Configuration newConfig = res.getConfiguration();
        if (systemFontScale > 0) {
            newConfig.fontScale = systemFontScale;
        }

        if (systemDensityDpi > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                newConfig.densityDpi = systemDensityDpi;
            }
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
            Log.e("rr", "restore:" + res.getDisplayMetrics());
        }

    }

    /**
     * 禁用7.0（23）以上显示大小改变和文字大小
     * 警告：仅做代码库使用，慎用，较小情况下会导致systemDensityDpi恢复异常，
     * 导致其他页面可能会出现字体忽大忽小的问题
     * 如有业务场景确实需要禁用字体大小和7.0之后显示大小时，看情况优先推荐使用px代替dp,sp，再尝试本方法
     * 使用参见{@link DensityNoneChangeDemoActivity}
     */
    public static Resources disabledDisplayDpiChange(Resources res) {
        Configuration newConfig = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //字体非默认值
            if (res.getConfiguration().fontScale != 1) {
                systemFontScale = res.getConfiguration().fontScale;
                newConfig.fontScale = 1;
            }
            int currentDensityDpi = newConfig.densityDpi;//目前手机真实的densitydpi
            int defaultDisplayDensity = getDefaultDisplayDensity();//手机系统默认的densitydpi
            systemDensityDpi = defaultDisplayDensity;
            Log.e("rr", "currentDensityDpi:" + currentDensityDpi + "\tdefaultDisplayDensity:" + defaultDisplayDensity);
            if (currentDensityDpi != defaultDisplayDensity) {//当currentDensityDpi和defaultDisplayDensity不相等时，说明修改了分辨率或显示大小或两者都修改了，需要进行一些特殊处理
                systemDensityDpi = currentDensityDpi;//记录目前真实的dpi，用于恢复
                if (currentDensityDpi < defaultDisplayDensity) {//当c小于d，说明调小了手机分辨率，或调小分辨率的同时调大了显示大小
                    int tmp = currentDensityDpi;
                    if (tmp % 160 != 0) {//由于大部分手机调小分辨率只能调为720/1080等，其dpi均为160整数倍，若不是整数倍，说明调小分辨率的同时调大了显示大小，这时应该将dpi改为调小分辨率而没调大显示大小的值
                        tmp = tmp - tmp % 160;
                    }
                    defaultDisplayDensity = tmp;//tmp此时为160整数倍，调小分辨率问题的显示问题解决
                }
            }
            newConfig.densityDpi = defaultDisplayDensity;//此时的default要不没变（只调大显示大小的情况，用默认dpi即可），要不就为160的整数倍（主要计算出调小分辨率后的defaultdpi）
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        } else {
            //字体非默认值
            if (res.getConfiguration().fontScale != 1) {
                newConfig.fontScale = 1;//设置默认
                res.updateConfiguration(newConfig, res.getDisplayMetrics());
            }
        }
        Log.e("rr", "change:" + res.getDisplayMetrics());
        return res;
    }
}
