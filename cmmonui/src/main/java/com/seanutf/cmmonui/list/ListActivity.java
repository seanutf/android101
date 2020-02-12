package com.seanutf.cmmonui.list;


import android.os.Bundle;
import android.view.View;

import com.seanutf.cmmonui.arch.BaseActivity;
import com.seanutf.cmmonui.databinding.ActivityListBinding;

public class ListActivity extends BaseActivity {

    ActivityListBinding layoutBind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBind = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(layoutBind.getRoot());


        layoutBind.tvSvRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 处理
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
}
