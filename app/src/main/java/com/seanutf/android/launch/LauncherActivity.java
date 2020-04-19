package com.seanutf.android.launch;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.seanutf.android.base.router.LauncherService;
import com.seanutf.android.utils.AppContext;
import com.seanutf.cmmonui.arch.BaseActivity;

import static com.seanutf.android.base.router.RouterPathConstant.SERVICE_APP_LAUNCHER;

public class LauncherActivity extends BaseActivity {

    @Autowired(name = SERVICE_APP_LAUNCHER)
    LauncherService launcherService;

    private static final int REQ_CODE_USER_PERMISSION = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }

    protected void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_CODE_USER_PERMISSION);
            } else {
                permissionAllow();
            }
        } else {
            permissionAllow();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQ_CODE_USER_PERMISSION:
                boolean allPermissionGranted = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        permissionDeny();
                        allPermissionGranted = false;
                        Toast.makeText(this, String.format("获取%s权限失败 ", permissions[i]), Toast.LENGTH_LONG).show();
                    }
                }
                if (allPermissionGranted) {
                    permissionAllow();
                }
                break;

            default:
                break;
        }
    }

    private void permissionAllow() {
        if (AppContext.isLaunchMain()) {
            launcherService.openMainUI();
            finishActivity();
        } else {
            launcherService.openWikiUI();
            finishActivity();
        }
    }

    private void permissionDeny() {
        finishActivity();
    }
}
