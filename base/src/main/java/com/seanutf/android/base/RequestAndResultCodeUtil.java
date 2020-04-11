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
     * 请求打开直播设置页面
     */
    public static final int REQ_OPEN_LIVE_SETTINGS = 10296;
    /**
     * 订单确认页面请求选择品牌券页面
     */
    public static final int REQ_SELECT_BRAND_COUPON = 10297;

    /**
     * 打开设置页
     */
    public static final int REQ_USER_SETTING = 10298;
    /**
     * 请求设置微信名片
     */
    public static final int REQ_SET_MY_WE_CHAT_CARD = 10299;
    /**
     * 请求系统相机录视频的功能
     */
    public static final int REQ_TAKE_VIDEO_BY_SYSTEM = 10300;

    /**
     * 请求应用内部录视频的功能
     */
    public static final int REQ_TAKE_VIDEO_BY_APP = 10301;

    /**
     * 请求更新社群介绍
     */
    public static final int REQ_UPDATE_SOCIAL_DESC = 10302;
    /**
     * 请求更新社群名称
     */
    public static final int REQ_UPDATE_SOCIAL_NAME = 10303;

    /**
     * 请求裁剪图片
     */
    public static final int REQ_MEDIA_IMG_CROP = 10304;


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


    /**
     * 请求获取特定类型的消息列表
     */
    public static final int REQ_SELECT_MEDIA = 10309;


    /**
     * 请求获取特定类型的消息列表
     */
    public static final int REQ_GET_MSG_TYPE_LIST = 10310;

    /**
     * 选择图片
     */
    public static final int REQ_SELECT_PIC = 10311;

    /**
     * 确认订单页面支付成功
     */
    public static final int REQ_ORDER_ADDRESS_LIST = 10312;

    /**
     * 确认订单页面支付成功
     */
    public static final int REQ_ORDER_PAY_SUCCESS = 10313;
    /**
     * 确认订单页面添加地址
     */
    public static final int REQ_ORDER_ADD_ADDR = 10314;
    /**
     * 确认订单页面更改地址
     */
    public static final int REQ_ORDER_UPDATE_ADDR = 10315;

    /**
     * 编辑店铺微信
     */
    public static final int REQ_SELECT_COUPON = 10316;

    /**
     * 编辑店铺微信
     */
    public static final int REQ_STORE_UPDATE_WECHAT = 10317;

    /**
     * 编辑店铺简介
     */
    public static final int REQ_STORE_UPDATE_INTRO = 10318;

    /**
     * 编辑店铺名称
     */
    public static final int REQ_STORE_UPDATE_NAME = 10319;
    /**
     * 编辑个人资料
     */
    public static final int REQ_EDIT_PROFILE = 10320;
    /**
     * 编辑昵称
     */
    public static final int REQ_EDIT_NICK = 10321;
    /**
     * 选择头像
     */
    public static final int REQ_SELECT_AVATAR = 10322;
    /**
     * 编辑个人介绍
     */
    public static final int REQ_EDIT_INTRODUCTION = 10323;

    //==========================  请求码  =======================================//
    //==========================  结果码  =======================================//
    /**
     * 订单确认页面对地址的更改
     */
    public static final int RES_ORDER_UPDATE_ADDRESS_OK = 10323;
    public static final int RES_ORDER_UPDATE_ADDRESS_CANCEL = 10324;

    /**
     * 订单确认页面支付成功
     */
    public static final int RES_ORDER_PAY_ACTIVITY_OK = 10325;

    /**
     * 粉丝列表关注和非关注的通知
     */
    public static final int RES_FANSLIST_DOSOMETHING = 10326;
    //==========================  结果码  =======================================//
}
