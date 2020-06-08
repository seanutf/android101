package com.seanutf.android.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.MergeAdapter;

import com.seanutf.android.base.databinding.ActivityTest2Binding;
import com.seanutf.cmmonui.arch.BaseActivity;

public class Test2Activity extends BaseActivity {

    ActivityTest2Binding vb;
    MergeAdapter mergeAdapter;

    public static void startActivity(BaseActivity aa) {
        aa.startActivity(new Intent(aa, Test2Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityTest2Binding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        //mergeAdapter.addAdapter();
    }
}
