package com.seanutf.android.commonutil;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class CommonUtilApp extends Application {

    public static CommonUtilApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initThirdParty();
    }

    private void initThirdParty() {
        initUserSetting();
    }

    private void initUserSetting() {
        MMKV.initialize(this);
    }
}
