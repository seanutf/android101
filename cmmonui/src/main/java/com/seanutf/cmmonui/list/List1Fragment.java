package com.seanutf.cmmonui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.seanutf.cmmonui.R;
import com.seanutf.cmmonui.arch.BaseFragment;
import com.seanutf.cmmonui.arch.BaseViewModel;

public class List1Fragment extends BaseFragment {

    private List1ViewModel mViewModel;

    public static List1Fragment newInstance() {
        return new List1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list1_fragment, container, false);
    }

    @Override
    protected BaseViewModel generateViewModel() {
        return ViewModelProviders.of(this).get(List1ViewModel.class);
    }
}
