package com.seanutf.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        final TextView tvText = findViewById(R.id.tvText);

        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvText.setText("hahhahah");
            }
        });

        tvText.setText("dddddd");
    }
}
