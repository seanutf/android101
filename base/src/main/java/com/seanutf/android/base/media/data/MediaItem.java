package com.seanutf.android.base.media.data;


import android.text.TextUtils;

/**
 * Created by sean on 2017/10/29.
 */

public class MediaItem extends Media {
    public float w2hRatio;           // [optional]宽高比
    public String picUrl;                    //[optional]图片类型数据 图片url或者视频封面图片url
    public String videoUrl;          // [optional]短视频数据 仅当mediaType=2时存在
    public String thumbnailUrl;     // [optional]缩略图url
    public String vid;            //头条播放器需要的播发vid
    public String playAuthToken;   //头条播发器需要的token

    //本地方法和字段
    public String getThumbUrl() {
        return TextUtils.isEmpty(thumbnailUrl) ? picUrl : thumbnailUrl;
    }

    public boolean isFirstSelect;  //用于媒体选择器判断是否第一次选择
    public boolean isSelect; //用于媒体选择器判断是否已被选择
}
