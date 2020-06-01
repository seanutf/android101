package com.seanutf.android.commonutil.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import androidx.core.app.NotificationManagerCompat;

import com.seanutf.android.commonutil.CommonUtilApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by sean on 16/2/22.
 */
public class AppUtil {
    private static float MemorySize;
    private static MemoryLevel memoryLevel;


    /**
     * 判断app是否在前台运行
     *
     * @return
     */
    public static boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) CommonUtilApp.instance.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = CommonUtilApp.instance.getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }


    public static int systemTimeForMinute() {
        long ts = System.currentTimeMillis();
        return (int) (ts / 1000);
    }

    //获取总内存大小
    public static float getTotalMemorySize(Context context) {
        if (MemorySize == 0) {
            float size = 0;

            //通过读取配置文件方式获取总内大小。文件目录：/proc/meminfo
            File file = new File("/proc/meminfo");
            FileInputStream fileInputStream = null;
            BufferedReader reader = null;
            InputStreamReader inputStreamReader = null;
            try {
                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream);
                reader = new BufferedReader(inputStreamReader);
                //根据命令行可以知道，系统总内存大小位于第一行
                String totalMemarysizeStr = reader.readLine();//MemTotal:         513744 kB
                //要获取大小，对字符串截取
                int startIndex = totalMemarysizeStr.indexOf(':');
                int endIndex = totalMemarysizeStr.indexOf('k');
                //截取
                totalMemarysizeStr = totalMemarysizeStr.substring(startIndex + 1, endIndex).trim();
                //转为long类型，得到数据单位是kb
                size = Float.parseFloat(totalMemarysizeStr);
                //转为以mb为单位
                size /= 1024f;
                //转为以gb为单位
                size /= 1024f;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fileInputStream != null) {
                        fileInputStream.close();
                    }

                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }

                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

            }
            MemorySize = size;
        }
        return MemorySize;
    }

    /**
     * 获取运行总内存
     * 3、6边界根据当前市场主流手机配置决定
     */
    public static MemoryLevel getMemoryLevel() {
        float size = getTotalMemorySize(CommonUtilApp.instance);
        if (size <= 3) {
            memoryLevel = MemoryLevel.low;
        } else if (size > 3 && size <= 6) {
            memoryLevel = MemoryLevel.normal;
        } else {
            memoryLevel = MemoryLevel.high;
        }

        return memoryLevel;
    }

    /**
     * 检查应用是否开启通知权限
     *
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {
        return NotificationManagerCompat.from(context).areNotificationsEnabled();
    }

    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     * @return
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            return className.equals(cpn.getClassName());
        }

        return false;

    }

    /**
     * 判断当前apk版本是否过低
     *
     * @param minVersionSupport  后台配置的最低支持的产品版本号，字符串
     * @param currentVersionName 当前apk的VersionName
     */
    public static boolean isAppVersionTooLow(String minVersionSupport, String currentVersionName) {
        //新旧版本号的字符串都为空，不升级
        if (!StringUtil.isNotEmptyString(minVersionSupport) || !StringUtil.isNotEmptyString(currentVersionName)) {
            return false;
        }
        String[] minVersionArr = minVersionSupport.split("\\.");
        String[] currentVersionArr = currentVersionName.split("\\.");

        //新旧版本的版本号都为空，不升级
        if (minVersionArr.length <= 0 || currentVersionArr.length <= 0) {
            return false;
        }

        //循环中：1.确保currentVersionArr的length大于等于lastVersionArr
        //2.确保两个数组中的元素都是字符串形式的数字
        //3.两个数组中的元素根据相同的位置比大小，只要last大于current就认为是有新版本
        for (int x = 0; x < minVersionArr.length; x++) {
            if (x < currentVersionArr.length && currentVersionArr[x] != null) {
                if (StringUtil.thisStringIsInteger(minVersionArr[x]) &&
                        StringUtil.thisStringIsInteger(currentVersionArr[x])) {
                    if (Integer.parseInt(minVersionArr[x]) > Integer.parseInt(currentVersionArr[x])) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * 判断当前apk是否是最新版本
     *
     * @param lastVersionName    后台配置的产品版本号，字符串
     * @param currentVersionName 当前apk的VersionName
     */
    public static boolean appCanUpdate(String lastVersionName, String currentVersionName) {
        //新旧版本号的字符串都为空，不升级
        if (!StringUtil.isNotEmptyString(lastVersionName) || !StringUtil.isNotEmptyString(currentVersionName)) {
            return false;
        }
        String[] lastVersionArr = lastVersionName.split("\\.");
        String[] currentVersionArr = currentVersionName.split("\\.");

        //新旧版本的版本号都为空，不升级
        if (lastVersionArr.length <= 0 || currentVersionArr.length <= 0) {
            return false;
        }

        //循环中：1.确保currentVersionArr的length大于等于lastVersionArr
        //2.确保两个数组中的元素都是字符串形式的数字
        //3.两个数组中的元素根据相同的位置比大小，只要last大于current就认为是有新版本
        for (int x = 0; x < lastVersionArr.length; x++) {
            if (x < currentVersionArr.length && currentVersionArr[x] != null) {
                if (StringUtil.thisStringIsInteger(lastVersionArr[x]) &&
                        StringUtil.thisStringIsInteger(currentVersionArr[x])) {
                    if (Integer.parseInt(lastVersionArr[x]) > Integer.parseInt(currentVersionArr[x])) {
                        return true;
                    } else if (Integer.parseInt(lastVersionArr[x]) < Integer.parseInt(currentVersionArr[x])) {
                        //版本号一直是递增态，所以一般不会进入这里，但因为是人工手动配置缘故，完善逻辑，容错
                        //之前没有这样判断，会出现lastVersionName1.7.3,currentVersionName1.8.0，根据最后3大于0，而提示升级，实际不需要
                        return false;
                    }
                }
            }
        }

        return false;
    }

    public enum MemoryLevel {
        low,
        normal,
        high
    }

    /**
     * 请将代码写在这个注释的上方
     * 注意：并不推荐继续更新这个类的代码
     * 如果所加入的代码是App全局性的，可以添加
     * 否则，请在util包下查看其他Util类，加入适合的Util类中
     * */
}
