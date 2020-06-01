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
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.cmmonui.util.ToastUtils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MultipleSelectAdapter extends BaseMediaSelectAdapter {

    private boolean mHadSelectedPhoto = false;
    private int currentSelectMaxNum;
    private List<MediaItem> currentSelectedMedias = new ArrayList<>();

    public MultipleSelectAdapter(@NotNull Context context, @NotNull OnClickMediaListener clickMediaListener, @Nullable CommonAdapterListener commonAdapterListener) {
        super(context, clickMediaListener, commonAdapterListener);

        if (CollectionUtils.isNotEmptyList(selectedImgList)) {
            currentSelectMaxNum = selectPhotoMaxNum - selectedImgList.size();
            if (selectedImgList.get(0) != null && !((MediaItem) selectedImgList.get(0)).isVideo()) {
                mHadSelectedPhoto = true;
            }
        }
    }

    @NotNull
    @Override
    protected RecyclerView.ViewHolder getViewHolder(@NotNull ViewGroup viewGroup, int viewType) {
        if (viewType == 1) {
            return null;
        }
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
                if (mediaItem.isVideo() && mHadSelectedPhoto) { //当有图片选中的时候，视频无法被选中
                    ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.VISIBLE);
                    ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.INVISIBLE);
                } else if (selectedIndex != -1) {
                    ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.VISIBLE);
                    ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.INVISIBLE);
                } else {
                    ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.INVISIBLE);
                    ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.VISIBLE);
                    int index = indexOfSelectedMedia(mediaItem);
                    if (index != -1) {
                        ((DefaultMediaItemViewHolder) holder).tvSelectNum.setSelected(true);
                        ((DefaultMediaItemViewHolder) holder).tvSelectNum.setBackgroundResource(R.drawable.ic_media_photo_selected);
                        ((DefaultMediaItemViewHolder) holder).tvSelectNum.setText(String.valueOf(index + 1));
                    } else {
                        if (currentSelectedMedias.size() >= currentSelectMaxNum) {
                            ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.VISIBLE);
                            ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.INVISIBLE);
                        } else {
                            ((DefaultMediaItemViewHolder) holder).unableSelectMask.setVisibility(View.INVISIBLE);
                            ((DefaultMediaItemViewHolder) holder).tvSelectNum.setVisibility(View.VISIBLE);
                        }
                        ((DefaultMediaItemViewHolder) holder).tvSelectNum.setSelected(false);
                        ((DefaultMediaItemViewHolder) holder).tvSelectNum.setBackgroundResource(R.drawable.ic_media_photo_unselect);
                        ((DefaultMediaItemViewHolder) holder).tvSelectNum.setText("");
                    }
                }


                ((DefaultMediaItemViewHolder) holder).unableSelectMask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                ((DefaultMediaItemViewHolder) holder).ivPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            File tempFile = new File(mediaItem.mediaPath);
                            if (!tempFile.exists()) {
                                ToastUtils.showToast(R.string.toast_media_select_none);
                                return;
                            }
                        } catch (Exception e) {
                            ToastUtils.showToast(R.string.toast_media_select_none);
                            return;
                        }

                        if (!mediaItem.isVideo()) {
                            int indexOfselectedList = indexOfSelectedList(mediaItem);
                            if (indexOfselectedList != -1) {
                                ToastUtils.showToast(R.string.img_selected);
                                return;
                            }

                            if (((DefaultMediaItemViewHolder) holder).tvSelectNum.isSelected()) {
                                removeSelectedMedia(mediaItem);
                                ((DefaultMediaItemViewHolder) holder).tvSelectNum.setSelected(false);
                                ((DefaultMediaItemViewHolder) holder).tvSelectNum.setBackgroundResource(R.drawable.ic_media_photo_unselect);
                                ((DefaultMediaItemViewHolder) holder).tvSelectNum.setText("");
                            } else {
                                if (currentSelectedMedias.size() >= currentSelectMaxNum) {
                                    ToastUtils.showToast("最多可选" + String.valueOf(currentSelectMaxNum) + "张照片");
                                    return;
                                }
                                currentSelectedMedias.add(mediaItem);
                                ((DefaultMediaItemViewHolder) holder).tvSelectNum.setSelected(true);
                                ((DefaultMediaItemViewHolder) holder).tvSelectNum.setBackgroundResource(R.drawable.ic_media_photo_selected);
                                int index = indexOfSelectedMedia(mediaItem);
                                ((DefaultMediaItemViewHolder) holder).tvSelectNum.setText(String.valueOf(index + 1));
                            }

                            if (!mediaItem.isVideo()) {
                                if (currentSelectedMedias.size() == 0) {
                                    mHadSelectedPhoto = false;
                                } else {
                                    mHadSelectedPhoto = true;
                                }
                            } else {
                                mHadSelectedPhoto = false;
                            }

                            notifyDataSetChanged();
                            if (clickMediaListener != null) {
                                clickMediaListener.onClickMedia(currentSelectedMedias);
                            }
                        } else {
                            if (clickMediaListener != null) {
                                clickMediaListener.onClickVideo(mediaItem);
                            }
                        }
                    }
                });
            }
        }
    }

    public int indexOfSelectedMedia(MediaItem mediaItem) {
        if (mediaItem != null) {
            int index = 0;
            for (MediaItem item : currentSelectedMedias) {
                if (item.mediaPath != null && item.mediaPath.equals(mediaItem.mediaPath)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public void removeSelectedMedia(MediaItem mediaItem) {
        if (mediaItem != null) {
            MediaItem tempitem = null;
            for (MediaItem item : currentSelectedMedias) {
                if (item.mediaPath != null && item.mediaPath.equals(mediaItem.mediaPath)) {
                    tempitem = item;
                    break;
                }
            }
            currentSelectedMedias.remove(tempitem);
        }
    }

    @Override
    public List<MediaItem> getCurrentMediaList() {
        return currentSelectedMedias;
    }

    @Override
    public List<MediaItem> getSelectedImageList() {
        return selectedImgList;
    }
}
