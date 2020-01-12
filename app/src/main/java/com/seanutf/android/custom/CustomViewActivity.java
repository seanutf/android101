package com.seanutf.android.custom;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Sean on 2020-01-13.
 */
public class CustomViewActivity extends AppCompatActivity {

    public static void startActivity(AppCompatActivity appCompatActivity){
        Intent intent = new Intent(appCompatActivity, CustomViewActivity.class);
        appCompatActivity.startActivity(intent);
    }
}
