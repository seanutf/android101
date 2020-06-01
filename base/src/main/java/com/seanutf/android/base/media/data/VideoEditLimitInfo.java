package com.seanutf.android.base.media.data;

import java.io.Serializable;

/*
 * 视频压缩的分辨率和码率限制，适用于不同的场景
 * 客服发送的视频为540*960，码率为5M，目的是限制大小在10m以内
 * 发布种草的视频分辨为720*12800，码率为6M
 **/
public class VideoEditLimitInfo implements Serializable {
    public int videoWith;
    public int videoHight;
    public int videoBitrate;

    public VideoEditLimitInfo(int videoWith, int videoHight, int videoBitrate) {
        this.videoWith = videoWith;
        this.videoHight = videoHight;
        this.videoBitrate = videoBitrate;
    }
}
