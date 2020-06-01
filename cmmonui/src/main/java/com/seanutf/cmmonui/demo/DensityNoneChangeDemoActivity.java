package com.seanutf.cmmonui.demo;

import android.content.res.Configuration;
import android.content.res.Resources;

import com.seanutf.cmmonui.arch.BaseActivity;
import com.seanutf.cmmonui.util.DensityUtil;

/**
 * 本页面作为禁用字体大小和7.0之后显示大小的示例页面
 * 仅做代码示例，不要在AndroidManifest.xml文件中注册本页面，
 * 也不要试图调用打开本页面
 */
public class DensityNoneChangeDemoActivity extends BaseActivity {

    boolean hasDisabledDpi;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {//禁止app字体大小跟随系统字体大小调节
        Resources res = super.getResources();
        if (hasDisabledDpi) {
            return res;
        }
        hasDisabledDpi = true;
        return DensityUtil.disabledDisplayDpiChange(res);
    }

    @Override
    public void finish() {
        DensityUtil.restoreSystemDisplayDip(super.getResources());
        super.finish();
    }
}
