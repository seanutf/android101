package com.seanutf.android.utils;

import com.seanutf.android.BuildConfig;

public class AppContext {

    private static final int MODE_LAUNCH_MAIN = 1;  //打开主界面
    private static final int MODE_LAUNCH_WIKI = 2;  //打开百科界面
    private static final int MODE_LAUNCH_TEST = 3;  //打开测试界面

    public static boolean isLaunchMain() {
        return BuildConfig.LAUNCH_ENVIRONMNET == MODE_LAUNCH_MAIN;
    }

    public static boolean isLaunchWiki() {
        return BuildConfig.LAUNCH_ENVIRONMNET == MODE_LAUNCH_WIKI;
    }
}
