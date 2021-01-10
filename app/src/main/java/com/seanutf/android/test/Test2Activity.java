package com.seanutf.android.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ConcatAdapter;

import com.seanutf.android.base.databinding.ActivityTest2Binding;
import com.seanutf.cmmonui.arch.BaseActivity;

import java.util.HashMap;
import java.util.Map;

public class Test2Activity extends BaseActivity {

    ActivityTest2Binding vb;
    ConcatAdapter mergeAdapter;

    public static void startActivity(BaseActivity aa) {
        aa.startActivity(new Intent(aa, Test2Activity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityTest2Binding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        Map<String, String> aa = new HashMap<>();
        Map<String, Object> bb = new HashMap<>();

        bb.putAll(aa);
        bb.put("dd", 1);
        bb.put("dd", "fsdfdsf");
        TestDataManager.INSTANCE.ff(aa);
        //mergeAdapter.addAdapter();
    }
}
