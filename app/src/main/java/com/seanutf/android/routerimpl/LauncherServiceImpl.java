package com.seanutf.android.routerimpl;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.seanutf.android.base.router.LauncherService;

import static com.seanutf.android.base.router.RouterPathConstant.SERVICE_APP_LAUNCHER;
import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_MAIN;
import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_TEST;
import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_WIKI;

@Route(path = SERVICE_APP_LAUNCHER, name = "启动服务")
public class LauncherServiceImpl implements LauncherService {

    @Override
    public void openMainUI() {
        ARouter.getInstance().build(UI_APP_MAIN).navigation();
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void openWikiUI() {
        ARouter.getInstance().build(UI_APP_WIKI).navigation();
    }

    @Override
    public void openTestUI() {
        ARouter.getInstance().build(UI_APP_TEST).navigation();
    }
}
