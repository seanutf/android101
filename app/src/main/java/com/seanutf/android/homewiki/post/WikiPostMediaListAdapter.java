package com.seanutf.android.homewiki.post;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.load.ImageLoader;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.android.databinding.ItemWikiPostMediaBinding;

import java.util.List;

public class WikiPostMediaListAdapter extends RecyclerView.Adapter {
    Context context;
    List<MediaItem> mediaItemList;

    public WikiPostMediaListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWikiPostMediaBinding itemMediaVb = ItemWikiPostMediaBinding.inflate(((Activity) parent.getContext()).getLayoutInflater(), parent, false);
        return new WikiPostMediaViewHolder(itemMediaVb);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MediaItem item = mediaItemList.get(position);
        if (item != null) {
            ImageLoader.loadImage(context, item.mediaPath, ((WikiPostMediaViewHolder) holder).itemWikiPostMediaBinding.ivPhoto);
        }
    }

    @Override
    public int getItemCount() {
        if (CollectionUtils.isNotEmptyList(mediaItemList)) {
            return mediaItemList.size();
        } else {
            return 0;
        }
    }
}
