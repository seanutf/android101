package com.seanutf.cmmonui.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;

import com.seanutf.android.commonutil.util.AppSettings;
import com.seanutf.android.commonutil.util.RomUtils;
import com.seanutf.cmmonui.arch.BaseActivity;

import java.lang.reflect.Method;

import static com.seanutf.cmmonui.util.StatusBarUtils.getStatusBarHeight;


/**
 * Created by sean on 2019/3/1.
 */
public class NotchUtil {

    private static int NOTCH_HEIGHT;

    public static int getNOTCH_HEIGHT() {
        if (NOTCH_HEIGHT <= 0) {
            NOTCH_HEIGHT = AppSettings.getInstance().getAppNotchHeight();
            return NOTCH_HEIGHT;
        }

        return NOTCH_HEIGHT;
    }

    public static void initNotchHeight(BaseActivity activity) {
        if (NOTCH_HEIGHT > 0)
            return;

        int height = 0;
        if (RomUtils.isEMUI() && hasNotchOnHuawei(activity))
            height = getNotchSizeOnHuawei(activity);

        if (height > 0)
            NOTCH_HEIGHT = height;
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            initAndroidNotchHeight(activity);
        } else {
            NOTCH_HEIGHT = getStatusBarHeight();
        }

        if (NOTCH_HEIGHT > 0) {
            AppSettings.getInstance().setAppNotchHeight(NOTCH_HEIGHT);
        }

    }

    /**
     * android 原生height
     */
    @TargetApi(Build.VERSION_CODES.P)
    private static void initAndroidNotchHeight(Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.post(() -> {
            int safeInsetTop;
            WindowInsets rootWindowInsets = decorView.getRootWindowInsets();
            if (rootWindowInsets == null) {
                NOTCH_HEIGHT = getStatusBarHeight();
            } else {
                DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
                if (displayCutout != null) {
                    safeInsetTop = displayCutout.getSafeInsetTop();
                    if (safeInsetTop > 0)
                        NOTCH_HEIGHT = safeInsetTop;
                    else
                        NOTCH_HEIGHT = getStatusBarHeight();
                } else {
                    NOTCH_HEIGHT = getStatusBarHeight();
                }
            }

            AppSettings.getInstance().setAppNotchHeight(NOTCH_HEIGHT);

        });
    }

    public static boolean hasNotchOnHuawei(Context context) {
        boolean ret = false;
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            //Log.e("Notch", "hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            //Log.e("Notch", "hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            //Log.e("Notch", "hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    //获取刘海尺寸：width、height
    //int[0]值为刘海宽度 int[1]值为刘海高度
    public static int getNotchSizeOnHuawei(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            //Log.e("Notch", "getNotchSizeAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            //Log.e("Notch", "getNotchSizeAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            //Log.e("Notch", "getNotchSizeAtHuawei Exception");
        } finally {
            return ret[1];
        }
    }

}
