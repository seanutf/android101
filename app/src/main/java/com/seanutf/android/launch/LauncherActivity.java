package com.seanutf.android.launch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.seanutf.android.base.aop.MyAnnotation;
import com.seanutf.android.base.router.LauncherService;
import com.seanutf.android.mvi.ui.MVITestActivity;
import com.seanutf.cmmonui.arch.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import static com.seanutf.android.base.router.RouterPathConstant.SERVICE_APP_LAUNCHER;

public class LauncherActivity extends BaseActivity {

    @Autowired(name = SERVICE_APP_LAUNCHER)
    LauncherService launcherService;

    private static final int REQ_CODE_USER_PERMISSION = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //testCountList();
        checkPermissions();
    }

    private void testCountList() {
        List<String> strList = new ArrayList<>();
        strList.add("1");
        strList.add("2");
        strList.add("3");
        strList.add("4");
        strList.add("5");
        strList.add("6");
        strList.add("7");

        for (int i = 0; i < strList.size(); i++) {
            Log.d("ListSubTest", "the strList has ele in " + i + " is " + strList.get(i));
        }

        List<String> subList1 = strList.subList(0, 3);
        if (subList1 instanceof ArrayList) {
            Log.d("ListSubTest", "the subList1 is ArrayList");
        } else {
            Log.d("ListSubTest", "the subList1 is not ArrayList");
        }

        strList.subList(0, 3).clear();

        if (strList instanceof ArrayList) {
            Log.d("ListSubTest", "the strList is ArrayList");
        } else {
            Log.d("ListSubTest", "the strList is not ArrayList");
        }

        if (strList != null) {
            Log.d("ListSubTest", "the strList is not null");
            Log.d("ListSubTest", "the strList size is :" + strList.size());
            if (strList.size() > 0) {
                for (int i = 0; i < strList.size(); i++) {
                    Log.d("ListSubTest", "the strList sub has ele in " + i + " is " + strList.get(i));
                }
            }
        } else {
            Log.d("ListSubTest", "the strList is null");
        }

    }
    @MyAnnotation
    protected void checkPermissions() {
        System.out.println("hui LauncherActivity checkPermissions");
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
//        MyTestThread t = new MyTestThread();
//        MyTestThread1 t1 = new MyTestThread1();
//        t.setDaemon(true);
//        t1.setDaemon(true);
//        t.start();
//        t1.start();
        Log.d("MyTestThread", "the Main is: " + Thread.currentThread().getName()
                + ",and the id is:" + Thread.currentThread().getId());
        startActivity(new Intent(this, MVITestActivity.class));

//        if (AppContext.isLaunchMain()) {
//            launcherService.openMainUI();
//            finishActivity();
//        } else if (AppContext.isLaunchWiki()) {
//            launcherService.openWikiUI();
//            finishActivity();
//        } else {
//            launcherService.openTestUI();
//            finishActivity();
//        }
    }

    private void permissionDeny() {
        finishActivity();
    }

    class MyTestThread extends Thread {
        @Override
        public void run() {
            super.run();
            Log.d("MyTestThread", "the MyTestThread is: " + Thread.currentThread().getName()
                    + ",and the id is:" + Thread.currentThread().getId());
        }
    }

    class MyTestThread1 extends Thread {
        @Override
        public void run() {
            super.run();
            Log.d("MyTestThread", "the MyTestThread1 is: " + Thread.currentThread().getName()
                    + ",and the id is:" + Thread.currentThread().getId());
        }
    }
}
