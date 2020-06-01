package com.seanutf.cmmonui.list;


import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.seanutf.cmmonui.arch.BaseActivity;
import com.seanutf.cmmonui.databinding.ActivityListBinding;

public class ListActivity extends BaseActivity {

    ActivityListBinding layoutBind;

    ScrollViewRecyclerViewFragment svRvFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBind = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(layoutBind.getRoot());


        layoutBind.tvSvRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSvRvFragment();
            }
        });

        layoutBind.tvCroRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 处理
            }
        });

        layoutBind.tvCroSv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 处理
            }
        });
    }

    private void showSvRvFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (svRvFragment == null) {
            svRvFragment = (ScrollViewRecyclerViewFragment) getSupportFragmentManager().findFragmentByTag(ScrollViewRecyclerViewFragment.TAG);
            if (svRvFragment == null) {
                svRvFragment = ScrollViewRecyclerViewFragment.newInstance();
            }
        }

        if (svRvFragment.isAdded()) {
            transaction.show(svRvFragment);
        } else {
            transaction.add(svRvFragment, ScrollViewRecyclerViewFragment.TAG);
        }

    }
}
