package com.seanutf.android.base.aop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.seanutf.android.base.R;

public class AopTest1Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aop_test1);
        initView();
    }

    @MyAnnotation
    private void initView() {
        System.out.println("xxxxxxxxxxxxxxxxxx AopTest1Activity initView");
    }

    public class Parent {

    }

    public class Son extends  Parent{

    }

    public static void main(String[] args){

    }
}