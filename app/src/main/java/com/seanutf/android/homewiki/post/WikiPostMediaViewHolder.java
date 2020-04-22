package com.seanutf.android.homewiki.post;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WikiPostMediaViewHolder extends RecyclerView.ViewHolder {

    com.seanutf.android.databinding.ItemWikiPostMediaBinding itemWikiPostMediaBinding;

    public WikiPostMediaViewHolder(@NonNull com.seanutf.android.databinding.ItemWikiPostMediaBinding itemWikiPostMediaBinding) {
        super(itemWikiPostMediaBinding.getRoot());
        this.itemWikiPostMediaBinding = itemWikiPostMediaBinding;
    }
}
