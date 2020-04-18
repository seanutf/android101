package com.seanutf.android.base.media.select.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.base.databinding.ListDirItemBinding;
import com.seanutf.android.base.media.data.AlbumData;
import com.seanutf.android.base.media.load.ImageLoader;
import com.seanutf.android.commonutil.util.CollectionUtils;

import java.util.List;

public class MediaAlbumAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<AlbumData> list;
    private AlbumData mSelectFolder;
    private OnClickAlbumCallback callback;

    public interface OnClickAlbumCallback {
        void onClick(AlbumData albumData);
    }

    public MediaAlbumAdapter(Context context, OnClickAlbumCallback callback, List<AlbumData> list) {
        this.context = context;
        this.list = list;
        this.callback = callback;
    }

    public void changeData(List<AlbumData> list, AlbumData selectedFolder) {
        mSelectFolder = selectedFolder;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListDirItemBinding itemBinding = ListDirItemBinding.inflate(LayoutInflater.from(context));
        return new MediaAlbumViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MediaAlbumViewHolder && list.get(position) != null) {
            AlbumData albumData = list.get(position);
            ImageLoader.loadImage(context, list.get(position).getFirstImagePath(), ((MediaAlbumViewHolder) holder).itemBinding.idDirItemImage);
            ((MediaAlbumViewHolder) holder).itemBinding.idDirItemName.setText(albumData.getName());
            ((MediaAlbumViewHolder) holder).itemBinding.idDirItemCount.setText(albumData.getCount() + "张");
            if (albumData == mSelectFolder) {
                ((MediaAlbumViewHolder) holder).itemBinding.idDirChoose.setVisibility(View.VISIBLE);
            } else {
                ((MediaAlbumViewHolder) holder).itemBinding.idDirChoose.setVisibility(View.INVISIBLE);
            }

            ((MediaAlbumViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onClick(albumData);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (CollectionUtils.isNotEmptyList(list)) {
            return list.size();
        }
        return 0;
    }

    static class MediaAlbumViewHolder extends RecyclerView.ViewHolder {

        ListDirItemBinding itemBinding;

        public MediaAlbumViewHolder(@NonNull ListDirItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }
}
