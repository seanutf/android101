package com.seanutf.android.base;

/**
 * Activity之间传递的请求码和结果码
 * 推荐使用，不强制，仅仅是为了方便全局统一管理
 * 对于requestCode使用1-10322之间的数，倒序添加
 * 对于resultCode使用10322之后的数，正序添加
 * resultCode推荐使用SDK提供的RESULT_OK和RESULT_CANCEL
 * 这里仅仅对之前app中的一些自定义的resultCode进行统一管理
 * 常量命名要能够准确含义
 * requestCode应该由发起页面定义
 * resultCode应该由结果页面定义
 */
public class RequestAndResultCodeUtil {


    //==========================  请求码  =======================================//
    /**
     * 请求系统相机录视频的功能
     */
    public static final int REQ_TAKE_VIDEO_BY_SYSTEM = 10300;

    /**
     * 请求应用内部录视频的功能
     */
    public static final int REQ_TAKE_VIDEO_BY_APP = 10301;

    /**
     * 请求裁剪图片
     */
    public static final int REQ_MEDIA_IMG_CROP = 10304;

    /**
     * 请求选择媒体
     */
    public static final int REQ_SELECT_MEDIA = 10309;

    /**
     * 请求编辑裁剪视频
     */
    public static final int REQ_MEDIA_VIDEO_PREVIEW = 10305;
    /**
     * 请求编辑裁剪视频
     */
    public static final int REQ_MEDIA_VIDEO_EDIT = 10306;

    /**
     * 发布过程中请求添加修改标签
     */
    public static final int REQ_UPDATE_TAG_TO_POST = 10307;

    /**
     * 发布过程中请求预览大图
     */
    public static final int REQ_PREVIEW_TO_POST = 10308;
    //==========================  请求码  =======================================//
    //==========================  结果码  =======================================//
    //==========================  结果码  =======================================//
}
