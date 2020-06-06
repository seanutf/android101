package com.seanutf.android.dagger;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.seanutf.android.R;
import com.seanutf.cmmonui.arch.BaseActivity;

public class DaggerActivity extends BaseActivity {

    public static void startActivity(AppCompatActivity activity){
        Intent intent = new Intent(activity, DaggerActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger);

        // Create an instance of the application graph
        ApplicationGraph applicationGraph = DaggerApplicationGraph.create();

        // Grab an instance of UserRepository from the application graph
        UserRepository userRepository = applicationGraph.userRepository();

        UserRepository userRepository2 = applicationGraph.userRepository();

        //assert(userRepository != userRepository2)

    }
}
