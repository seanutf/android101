package com.seanutf.android.homewiki.post;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.seanutf.android.databinding.ActivityHomeWikiTagBinding;
import com.seanutf.cmmonui.arch.BaseActivity;

public class HomeWikiTagListActivity extends BaseActivity {

    ActivityHomeWikiTagBinding vb;

    public static void startActivity(BaseActivity activity) {
        activity.startActivity(new Intent(activity, HomeWikiTagListActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityHomeWikiTagBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        initData();
    }

    private void initData() {

    }
}
