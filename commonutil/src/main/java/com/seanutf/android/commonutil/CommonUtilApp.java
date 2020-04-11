package com.seanutf.android.commonutil;

import android.app.Application;

public class CommonUtilApp extends Application {

    public static CommonUtilApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
