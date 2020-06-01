package com.seanutf.android.base.media.select.adapter;

import android.content.Context;

import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.data.VideoEditLimitInfo;
import com.seanutf.android.base.media.select.MediaSelectManager;
import com.seanutf.cmmonui.adapter.CommonRecyclerViewAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseMediaSelectAdapter<T extends MediaItem> extends CommonRecyclerViewAdapter {

    OnClickMediaListener clickMediaListener;

    MediaSelectManager.ScanMode scanMode; //扫描模式
    MediaSelectManager.AdapterMode adapterMode;
    VideoEditLimitInfo videoEditLimitInfo; //视频的宽高和码率
    int selectPhotoMaxNum;
    List<MediaItem> selectedImgList;//已选取图片列表
    String mediaSavePath; //自定义选中视频所保存的路径(视频编辑后)
    boolean needImgCrop; //单图模式下，图片是否需要裁剪
    boolean selectForChat; //是否为聊天选择的媒体
    int itemStyle;
    int spanCount;
    int cropImgW; //剪切图片宽
    int cropImgH; //剪切图片高

    public interface OnClickMediaListener {
        /**
         * 点击了一个视频，
         * 单选模式和多选模式都可能会调用，适用于视频
         */
        void onClickVideo(MediaItem mediaItem);

        /**
         * 点击了一个图片，
         * 只有Adapter是单选模式时调用
         */
        void onClickImg(MediaItem mediaItem);

        /**
         * 点击了一个图片，图片是否为选中状态
         * 只有Adapter是单选模式时调用
         */
        void onClickImg(boolean isSelect);

        /**
         * 点击了一个媒体，
         * 只有Adapter是多选模式调用
         * 返回当前选中的图片列表
         */
        void onClickMedia(List<MediaItem> itemList);
    }

    public BaseMediaSelectAdapter(@NotNull Context context, @NotNull OnClickMediaListener clickMediaListener, @Nullable CommonAdapterListener commonAdapterListener) {
        super(context, commonAdapterListener);
        this.clickMediaListener = clickMediaListener;
    }

    public void setConfig(MediaSelectManager.ScanMode scanMode,
                          MediaSelectManager.AdapterMode adapterMode,
                          VideoEditLimitInfo videoEditLimitInfo,
                          int selectPhotoMaxNum,
                          List<MediaItem> selectedImgList,
                          String mediaSavePath,
                          boolean needImgCrop,
                          boolean selectForChat,
                          int itemStyle,
                          int spanCount,
                          int cropImgW,
                          int cropImgH) {
        this.scanMode = scanMode;
        this.adapterMode = adapterMode;
        this.videoEditLimitInfo = videoEditLimitInfo;
        this.selectPhotoMaxNum = selectPhotoMaxNum;
        this.selectedImgList = selectedImgList;
        this.mediaSavePath = mediaSavePath;
        this.needImgCrop = needImgCrop;
        this.selectForChat = selectForChat;
        this.itemStyle = itemStyle;
        this.spanCount = spanCount;
        this.cropImgW = cropImgW;
        this.cropImgH = cropImgH;

    }

    public int indexOfSelectedList(MediaItem mediaItem) {
        if (selectedImgList == null)
            return -1;

        if (mediaItem != null) {
            int index = 0;
            for (MediaItem item : selectedImgList) {
                if (item.mediaPath != null && item.mediaPath.equals(mediaItem.mediaPath)) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public abstract List<MediaItem> getCurrentMediaList();

    public abstract List<MediaItem> getSelectedImageList();
}
