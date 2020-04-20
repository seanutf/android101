package com.seanutf.android.commonutil.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.seanutf.android.commonutil.CommonUtilApp;
import com.tencent.mmkv.MMKV;

/**
 * Created by Sean on 2020-01-12.
 * 新增的用户设定可在本类中进行
 */
public class AppSettings {

    //迁移数据相关
    private static final String KEY_MIGRATE_DONE = "KEY_MIGRATE_DONE";
    private static final String USER_SETTING_NAME = "android101";
    private static final String PHONE_DEVICE_ID = "phone_device_id";
    private static final String MANAGER_ID = "manager_id";
    private static final String APP_NOTCH_HEIGHT = "app_notch_height";
    private static final String HOME_WIKI_POST_SORT = "HOME_WIKI_POST_SORT";


    private static AppSettings instance;
    private static boolean migrateDone;
    private static MMKV mPreferences;

    private AppSettings() {

    }

    public synchronized static AppSettings getInstance() {
        if (instance == null) {
            instance = new AppSettings();
            mPreferences = MMKV.mmkvWithID(USER_SETTING_NAME, MMKV.MULTI_PROCESS_MODE);
            if (!migrateDone) {
                if (mPreferences.contains(KEY_MIGRATE_DONE) && mPreferences.decodeBool(KEY_MIGRATE_DONE)) {
                    //防止有不同步的情况
                    migrateDone = true;
                } else {
                    //迁移老数据
                    SharedPreferences oldPreferences = CommonUtilApp.instance.getSharedPreferences(USER_SETTING_NAME, Context.MODE_PRIVATE);
                    mPreferences.importFromSharedPreferences(oldPreferences);
                    //设置迁移完成
                    mPreferences.encode(KEY_MIGRATE_DONE, true);
                    migrateDone = true;
                    oldPreferences.edit().clear().commit();
                }
            }
        }
        return instance;
    }


    private void putBoolean(String key, boolean value) {
        if (null != mPreferences) {
            MMKV.Editor editor = mPreferences.edit();
            editor.putBoolean(key, value);
        }
    }

    private boolean getBoolean(String key) {
        if (null != mPreferences) {
            return mPreferences.getBoolean(key, false);
        }
        return false;
    }

    private boolean getBoolean(String key, boolean defaultValue) {
        if (null != mPreferences) {
            return mPreferences.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }

    private void putString(String key, String value) {
        if (null != mPreferences) {
            MMKV.Editor editor = mPreferences.edit();
            editor.putString(key, value);
        }
    }

    private String getString(String key) {
        if (null != mPreferences) {
            return mPreferences.getString(key, null);
        }
        return null;
    }

    private void putInt(String key, int value) {
        if (null != mPreferences) {
            MMKV.Editor editor = mPreferences.edit();
            editor.putInt(key, value);
        }
    }

    private void putLong(String key, long value) {
        if (null != mPreferences) {
            MMKV.Editor editor = mPreferences.edit();
            editor.putLong(key, value);
        }
    }

    private long getLong(String key) {
        if (null != mPreferences) {
            return mPreferences.getLong(key, 0);
        }
        return 0;
    }

    private int getInt(String key) {
        if (null != mPreferences) {
            return mPreferences.getInt(key, 0);
        }
        return 0;
    }

    public String getDeviceId() {
        return getString(PHONE_DEVICE_ID);
    }

    public void setDeviceId(String deviceId) {
        putString(PHONE_DEVICE_ID, deviceId);
    }

    public String getManagerId() {
        return getString(MANAGER_ID);
    }

    public void setManagerId(String id) {
        putString(MANAGER_ID, id);
    }


    public int getAppNotchHeight() {
        return getInt(APP_NOTCH_HEIGHT);
    }

    /**
     * 保存刘海屏或状态栏高度 应用崩溃后从这里面读取
     *
     * @param notchHeight
     */
    public void setAppNotchHeight(int notchHeight) {
        putInt(APP_NOTCH_HEIGHT, notchHeight);
    }


    public void setWikiPostSort(int sortId) {
        putInt(HOME_WIKI_POST_SORT, sortId);
    }

    public int getWikiPostSort() {
        return getInt(HOME_WIKI_POST_SORT);
    }
}
