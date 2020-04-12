package com.seanutf.cmmonui.arch;

import android.view.View;

public abstract class BaseCustomFragment extends BaseFragment {

    @Override
    protected View getCustomLayout() {
        return getChildLayout();
    }

    @Override
    protected final boolean isCustomLayout() {
        return true;
    }

    protected abstract View getChildLayout();
}
