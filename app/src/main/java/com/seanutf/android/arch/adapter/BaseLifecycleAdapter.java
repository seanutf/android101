package com.seanutf.android.arch.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseLifecycleAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BaseLifecycleViewHolder){
            ((BaseLifecycleViewHolder)holder).registerLifecycle(true);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
