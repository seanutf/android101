package com.seanutf.android.homewiki.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.databinding.ItemWikiListBinding;

public class HomeWikiListViewHolder extends RecyclerView.ViewHolder {

    public ItemWikiListBinding vbItem;

    public HomeWikiListViewHolder(@NonNull ItemWikiListBinding vbItem) {
        super(vbItem.getRoot());
        this.vbItem = vbItem;
    }
}
