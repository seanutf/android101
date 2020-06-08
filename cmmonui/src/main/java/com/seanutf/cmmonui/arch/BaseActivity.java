package com.seanutf.cmmonui.arch;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.launcher.ARouter;

public class BaseActivity extends AppCompatActivity {

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        init(savedInstanceState, persistentState);
    }

    private void init(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        ARouter.getInstance().inject(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("家庭百科");
        }
    }

    protected void finishActivity() {
        finish();
    }

    protected void openActivity(String path) {
        ARouter.getInstance().build(path).navigation();
    }

}
