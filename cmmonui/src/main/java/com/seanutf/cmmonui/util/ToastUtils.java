package com.seanutf.cmmonui.util;

import android.widget.Toast;

import com.seanutf.android.commonutil.CommonUtilApp;


public class ToastUtils {


    public ToastUtils() {
    }

    public static void showToast(String text) {
        Toast.makeText(CommonUtilApp.instance, text, Toast.LENGTH_SHORT).show();
    }

    public static void showLongToast(String text) {
        Toast.makeText(CommonUtilApp.instance, text, Toast.LENGTH_LONG).show();
    }

    public static void showToast(int resId) {
        showToast(CommonUtilApp.instance.getString(resId));
    }

    public static void showLongToast(int resId) {
        Toast.makeText(CommonUtilApp.instance, CommonUtilApp.instance.getString(resId), Toast.LENGTH_LONG).show();
    }

}
