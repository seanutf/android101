package com.seanutf.android.test;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.seanutf.android.base.databinding.ActivityTest1Binding;
import com.seanutf.android.commonutil.util.AppSettings;
import com.seanutf.cmmonui.arch.BaseActivity;

import static com.seanutf.android.base.router.RouterPathConstant.UI_APP_TEST;

/**
 * 测试Activtity每次都新建Fragment,Fragment的状态保存
 */
@Route(path = UI_APP_TEST)
public class Test1Activity extends BaseActivity {

    ActivityTest1Binding vb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityTest1Binding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        addFragment();
    }

    private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Test1Fragment fragment = null;
        fragment = (Test1Fragment) fragmentManager.findFragmentByTag(Test1Fragment.TAG);
        if (fragment == null) {
            fragment = new Test1Fragment();
            Log.d("FrragmentTest", "Test1Activity create the fragment " + fragment.toString());
        } else {
            Log.d("FrragmentTest", "Test1Activity restore the fragment " + fragment.toString());
        }

        Bundle bundle = new Bundle();
        int openId = AppSettings.getInstance().getTestOpenId();
        bundle.putInt("id", openId);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(vb.llFragmentContent.getId(), fragment, Test1Fragment.TAG);
        }
        transaction.commitNowAllowingStateLoss();
        AppSettings.getInstance().setTestOpenId(openId + 1);
    }
}
