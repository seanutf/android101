package com.seanutf.cmmonui.arch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseContentFragment extends BaseFragment {

    @Override
    protected View getContentLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getChildLayout(inflater, container, savedInstanceState);
    }

    @Override
    protected final boolean isCustomLayout() {
        return false;
    }

    protected abstract View getChildLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
