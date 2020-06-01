package com.seanutf.android.commonutil;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.seanutf.android.commonutil.util.AppSettings;
import com.seanutf.android.commonutil.util.StringUtil;

/**
 * Created by Sean on 2020-01-12.
 */
public class AppInfo {

    /**
     * 环境类型
     * 请不要为了开发调试，改动这段代码，请改动version.properties文件
     */
    private static int isTestMode = BuildConfig.CURRENT_ENVIRONMNET;

    /**
     * 版本类型
     */
    public static boolean isDebug = false;


    public static boolean isDebug() {
        return BuildConfig.DEBUG || isDebug;
    }

    public void changeDebug() {
        isDebug = !isDebug;
    }

    public static boolean isTest() {
        return isTestMode == BuildConfig.TEST_ENVIRONMNET;
    }

    public static String getCurrentAppVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public static String getIMEI() {
        String deviceId = AppSettings.getInstance().getDeviceId();
        if (StringUtil.isNotEmptyStringAbsolute(deviceId)) {
            return deviceId;
        } else {
            TelephonyManager tm = (TelephonyManager) CommonUtilApp.instance.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) {
                deviceId = tm.getDeviceId();
                if (!TextUtils.isEmpty(deviceId)) {
                    AppSettings.getInstance().setDeviceId(deviceId);
                    return deviceId;
                }
            }
        }

        String uid = AppSettings.getInstance().getManagerId();
        if (StringUtil.isNotEmptyStringAbsolute(uid)) {
            return uid;
        } else {
            String id = Settings.Secure.getString(CommonUtilApp.instance.getContentResolver(), Settings.Secure.ANDROID_ID);
            AppSettings.getInstance().setManagerId(id);
            return id;
        }
    }

    /**
     * 后台需要根据app请求的版本号来决定是否进入测试环境：线上版本号：线上环境；线上版本号前加1，进入测试环境
     */
    public static String versionString() {
        return BuildConfig.VERSION_NAME;
    }
}
