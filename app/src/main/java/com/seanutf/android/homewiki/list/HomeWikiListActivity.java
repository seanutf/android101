package com.seanutf.android.homewiki.list;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seanutf.android.databinding.ActivityHomeWikiBinding;
import com.seanutf.android.homewiki.post.HomeWikiPostActivity;
import com.seanutf.cmmonui.arch.BaseActivity;

import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_WIKI;

@Route(path = UI_APP_WIKI)
public class HomeWikiListActivity extends BaseActivity {

    ActivityHomeWikiBinding vb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityHomeWikiBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setViews();
    }

    private void setViews() {
        vb.tvBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeWikiPostActivity.startActivity(HomeWikiListActivity.this);
            }
        });
    }
}
