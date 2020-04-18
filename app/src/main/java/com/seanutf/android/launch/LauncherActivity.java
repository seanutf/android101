package com.seanutf.android.launch;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.seanutf.android.base.router.LauncherService;
import com.seanutf.android.utils.AppContext;
import com.seanutf.cmmonui.arch.BaseActivity;

import static com.seanutf.android.base.router.RouterPathConstant.SERVICE_APP_LAUNCHER;

public class LauncherActivity extends BaseActivity {

    @Autowired(name = SERVICE_APP_LAUNCHER)
    LauncherService launcherService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //launcherService = ARouter.getInstance().navigation(LauncherService.class);
        if (AppContext.isLaunchMain()) {
            launcherService.openMainUI();
            finishActivity();
        } else {
            launcherService.openWikiUI();
            finishActivity();
        }
    }
}
