package com.seanutf.cmmonui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.seanutf.cmmonui.R;
import com.seanutf.cmmonui.arch.BaseFragment;
import com.seanutf.cmmonui.arch.BaseViewModel;

public class ScrollViewRecyclerViewFragment extends BaseFragment {

    public static final String TAG = ScrollViewRecyclerViewFragment.class.getSimpleName();

    public static ScrollViewRecyclerViewFragment newInstance() {
        return new ScrollViewRecyclerViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list1_fragment, container, false);
    }

    @Override
    protected BaseViewModel generateViewModel() {
        return new ViewModelProvider(this).get(List1ViewModel.class);
    }
}
