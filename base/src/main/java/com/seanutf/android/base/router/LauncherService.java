package com.seanutf.android.base.router;

import com.alibaba.android.arouter.facade.template.IProvider;

public interface LauncherService extends IProvider {

    void openMainUI();

    void openWikiUI();

    void openTestUI();
}
