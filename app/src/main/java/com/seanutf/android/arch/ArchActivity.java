package com.seanutf.android.arch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.seanutf.android.R;
import com.seanutf.android.databinding.ActivityArchBinding;
import com.seanutf.cmmonui.BaseActivity;

public class ArchActivity extends BaseActivity {

    ActivityArchBinding archBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        archBinding = ActivityArchBinding.inflate(getLayoutInflater());
        setContentView(archBinding.getRoot());
        initView();
    }

    private void initView() {
        archBinding.tvText.setText("明天会更好");
    }
}
