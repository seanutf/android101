package com.seanutf.android.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seanutf.android.base.databinding.FragmentTest1Binding;
import com.seanutf.cmmonui.arch.BaseActivity;

public class Test1Fragment extends Fragment {
    String aa = null;
    FragmentTest1Binding vb;
    private static final String KEY_ID = "KEY_ID";
    public static final String TAG = Test1Fragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FrragmentTest", "the fragment " + this.toString() + " call onCreate(), and the aa is: " + aa);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vb = FragmentTest1Binding.inflate(inflater, container, false);
        return vb.getRoot();
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        int id = args.getInt("id");
        aa = id + "cc";
        Log.d("FrragmentTest", "the fragment " + this.toString() + " call setArguments(), and the aa is: " + aa);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getString(KEY_ID) != null) {
                aa = savedInstanceState.getString(KEY_ID);
                Log.d("FrragmentTest", "the fragment " + this.toString() + " call onViewCreated(), and the aa is restored : " + aa);
            }
        } else {
            Log.d("FrragmentTest", "the fragment " + this.toString() + " call onViewCreated(), and the aa is normal : " + aa);
        }

        vb.tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test2Activity.startActivity((BaseActivity) getActivity());
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("FrragmentTest", "the fragment " + this.toString() + " call onSaveInstanceState(), and save aa is : " + aa);
        outState.putString(KEY_ID, aa);
    }
}
