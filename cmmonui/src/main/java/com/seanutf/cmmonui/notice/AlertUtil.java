package com.seanutf.cmmonui.notice;

import android.app.Activity;

import com.seanutf.cmmonui.CommonUIApp;
import com.seanutf.cmmonui.R;
import com.seanutf.cmmonui.util.ToastUtils;
import com.tapadoo.alerter.Alerter;

/**
 * Created by sean on 2019-07-12.
 */
public class AlertUtil {


    public static void showAlert(String text) {
        Activity topActivityInstance = CommonUIApp.instance.getTopActivityInstance();
        if (topActivityInstance != null && !topActivityInstance.isFinishing() && !topActivityInstance.isDestroyed()) {
            Alerter.create(topActivityInstance)
                    .setText(text)
                    .setDuration(2000)
                    .setBackgroundColorRes(R.color.colorAccent)
                    .showIcon(false)
                    //.setTextAppearance(R.style.tv_30_ffffff)
                    .show();
        } else {
            ToastUtils.showToast(text);
        }
    }

    public static void showAlert(int resId) {
        showAlert(CommonUIApp.instance.getString(resId));
    }

    public static void showLongAlert(String text) {
    }

    public static void showLongAlert(int resId) {
    }

}
