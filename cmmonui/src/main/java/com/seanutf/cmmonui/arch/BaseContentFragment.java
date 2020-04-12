package com.seanutf.cmmonui.arch;

import android.view.View;

public abstract class BaseContentFragment extends BaseFragment {
    @Override
    protected View getContentLayout() {
        return getChildLayout();
    }

    @Override
    protected final boolean isCustomLayout() {
        return false;
    }

    protected abstract View getChildLayout();
}
