package com.seanutf.android.arch;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.seanutf.android.arch.data.User;
import com.seanutf.android.databinding.ActivityArchBinding;
import com.seanutf.cmmonui.BaseActivity;

import java.util.List;

public class ArchActivity extends BaseActivity {

    ActivityArchBinding archBinding;

    MyViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        archBinding = ActivityArchBinding.inflate(getLayoutInflater());
        setContentView(archBinding.getRoot());
        //initView();
        model = new OrderViewModelFactory().create(MyViewModel.class);

        model.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                // update UI
            }
        });
    }

    private void initView() {
        //优化
        archBinding.tvText.setText("明天会更好");
    }
}
