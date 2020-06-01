package com.seanutf.cmmonui.arch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseCustomFragment extends BaseFragment {

    @Override
    protected View getCustomLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getChildLayout(inflater, container, savedInstanceState);
    }

    @Override
    protected final boolean isCustomLayout() {
        return true;
    }

    protected abstract View getChildLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);
}
