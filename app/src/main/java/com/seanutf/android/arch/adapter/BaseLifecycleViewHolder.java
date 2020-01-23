package com.seanutf.android.arch.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

public class BaseLifecycleViewHolder extends RecyclerView.ViewHolder implements LifecycleOwner {

    public BaseLifecycleViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return null;
    }

    public void registerLifecycle(boolean resetVersion){

    }
}
