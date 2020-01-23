package com.seanutf.android.arch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.seanutf.android.R;
import com.seanutf.android.databinding.ActivityArchBinding;

public class ArchActivity extends AppCompatActivity {

    ActivityArchBinding archBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        archBinding = ActivityArchBinding.inflate(getLayoutInflater());
        setContentView(archBinding.getRoot());
    }
}
