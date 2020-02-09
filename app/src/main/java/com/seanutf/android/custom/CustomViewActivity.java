package com.seanutf.android.custom;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.seanutf.android.databinding.ActivityCustomViewBinding;
import com.seanutf.cmmonui.arch.BaseActivity;

/**
 * Created by Sean on 2020-01-13.
 */
public class CustomViewActivity extends BaseActivity {

    public static void startActivity(AppCompatActivity appCompatActivity){
        Intent intent = new Intent(appCompatActivity, CustomViewActivity.class);
        appCompatActivity.startActivity(intent);
  }

    ActivityCustomViewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityCustomViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}
