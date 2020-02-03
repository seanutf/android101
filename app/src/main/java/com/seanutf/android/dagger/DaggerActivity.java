package com.seanutf.android.dagger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.seanutf.android.R;

public class DaggerActivity extends AppCompatActivity {

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
