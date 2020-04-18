package com.seanutf.android.homewiki;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seanutf.android.databinding.ActivityHomeWikiBinding;
import com.seanutf.cmmonui.arch.BaseActivity;

import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_WIKI;

@Route(path = UI_APP_WIKI)
public class HomeWikiActivity extends BaseActivity {

    ActivityHomeWikiBinding vb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityHomeWikiBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
    }
}
