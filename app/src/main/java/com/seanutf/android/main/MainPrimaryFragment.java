package com.seanutf.android.main;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.seanutf.cmmonui.arch.BaseFragment;

public class MainPrimaryFragment extends BaseFragment<MainPrimaryViewModel> {

    @Override
    protected MainPrimaryViewModel generateViewModel() {
        return new MainPrimaryViewModel();
    }


    @Override
    protected void handle() {
    }

    @Override
    protected void setViews(@Nullable Bundle savedInstanceState) {

    }
}
