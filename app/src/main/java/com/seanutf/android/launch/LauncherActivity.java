package com.seanutf.android.launch;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.seanutf.android.BuildConfig;
import com.seanutf.cmmonui.arch.BaseActivity;

public class LauncherActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.LAUNCH_ENVIRONMNET == 1) {
            //MainActivity
        } else {

        }
    }


}
