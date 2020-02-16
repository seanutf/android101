package com.seanutf.cmmonui.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.seanutf.cmmonui.arch.BaseFragment;
import com.seanutf.cmmonui.databinding.List1FragmentBinding;

import java.util.List;

public class ScrollViewRecyclerViewFragment extends BaseFragment<List1ViewModel> implements NestedScrollView.OnScrollChangeListener {

    public static final String TAG = ScrollViewRecyclerViewFragment.class.getSimpleName();

    List1FragmentBinding vb;
    public static ScrollViewRecyclerViewFragment newInstance() {
        return new ScrollViewRecyclerViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vb = List1FragmentBinding.inflate(inflater);
        return vb.getRoot();
    }

    @Override
    protected List1ViewModel generateViewModel() {
        return new ViewModelProvider(this).get(List1ViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.getSvData().observe(getViewLifecycleOwner(), new Observer<List<SvData>>() {
            @Override
            public void onChanged(List<SvData> svData) {

            }
        });

        vb.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

    }
}
