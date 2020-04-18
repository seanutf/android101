package com.seanutf.android.base.media.select.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.seanutf.android.base.R;
import com.seanutf.android.base.media.MediaUtil;
import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.load.ImageLoader;
import com.seanutf.cmmonui.util.DensityUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class SingleSelectAdapter extends BaseMediaSelectAdapter {

    int itemImgWidth;

    public SingleSelectAdapter(@NotNull Context context, @NotNull OnClickMediaListener clickMediaListener, @Nullable CommonAdapterListener commonAdapterListener) {
        super(context, clickMediaListener, commonAdapterListener);
        itemImgWidth = (int) (DensityUtil.getScreenWidth() - DensityUtil.dp2px(6)) / 3;
    }

    @NotNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_media_select_default, viewGroup, false);
        return new DefaultMediaItemViewHolder(itemView);
    }

    @Override
    protected int getItemType(int position) {
        if (itemStyle == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    protected void bind(@NotNull RecyclerView.ViewHolder holder, int position) {
        final MediaItem mediaItem = (MediaItem) getData().get(position);
        if (mediaItem != null) {

            if (holder instanceof DefaultMediaItemViewHolder) {
                if (mediaItem.isVideo()) {
                    Glide.with(getContext()).load(mediaItem.mediaPath).transition(withCrossFade()).into(((DefaultMediaItemViewHolder) holder).ivPhoto);
                    String duration = MediaUtil.makeDuration(mediaItem.duration / 1000);
                    if (duration != null && duration.length() > 0) {
                        ((DefaultMediaItemViewHolder) holder).tvVideoDuration.setText(duration);
                    } else {
                        ((DefaultMediaItemViewHolder) holder).tvVideoDuration.setText("--:--");
                    }
                    ((DefaultMediaItemViewHolder) holder).tvVideoDuration.setVisibility(View.VISIBLE);
                    ((DefaultMediaItemViewHolder) holder).videoTag.setVisibility(View.VISIBLE);
                    ((DefaultMediaItemViewHolder) holder).llSelectNum.setVisibility(View.GONE);
                } else {
                    ImageLoader.loadImage(getContext(), mediaItem.mediaPath, ((DefaultMediaItemViewHolder) holder).ivPhoto);
                    ((DefaultMediaItemViewHolder) holder).tvVideoDuration.setVisibility(View.INVISIBLE);
                    ((DefaultMediaItemViewHolder) holder).videoTag.setVisibility(View.INVISIBLE);
                    ((DefaultMediaItemViewHolder) holder).llSelectNum.setVisibility(View.VISIBLE);
                }

                int selectedIndex = indexOfSelectedList(mediaItem);
                if (selectedIndex != -1) {
                    ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.VISIBLE);
                    ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.INVISIBLE);
                } else {
                    ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.INVISIBLE);
                    ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.INVISIBLE);
                }

                ((DefaultMediaItemViewHolder) holder).ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickMediaListener != null) {
                            if (mediaItem.isVideo()) {
                                addItem(mediaItem);
                                clickMediaListener.onClickVideo(mediaItem);
                            } else {
                                addItem(mediaItem);
                                clickMediaListener.onClickImg(mediaItem);
                            }
                        }
                    }
                });
            }
        }
    }

    private void addItem(MediaItem mediaItem) {
        if (selectedImgList != null) {
            selectedImgList.clear();
            selectedImgList.add(mediaItem);
        }
    }

    @Override
    public List<MediaItem> getCurrentMediaList() {
        return null;
    }

    @Override
    public List<MediaItem> getSelectedImageList() {
        return selectedImgList;
    }
}
