package com.seanutf.android.base.media.select;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.data.VideoEditLimitInfo;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.cmmonui.util.ToastUtils;

import java.io.Serializable;
import java.util.List;

import static com.seanutf.android.base.IntentKeyUtil.KEY_LIST_MEDIA;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_IMG_CROP_H;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_IMG_CROP_W;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_ADAPTER_MODE;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_FOR_CHAT;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_IMG_NEED_CROP;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_ITEM_STYLE;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_MAX_NUM;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_SAVE_PATH;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_SCAN_MODE;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_SPAN_COUNT;
import static com.seanutf.android.base.IntentKeyUtil.KEY_SELECT_MEDIA_VIDEO_DETAILS;
import static com.seanutf.android.base.RequestAndResultCodeUtil.REQ_SELECT_MEDIA;

public class MediaSelectManager {

    public static final int MAX_SELECT_MEDIA_NUM_DEFAULT = 9;  //默认的多图模式下选取图片最大数

    public static final int SPAN_COUNT_GRID_DEFAULT = 3;

    public static final int DEFAULT_IMG_CROP_WIDTH = 750;
    public static final int DEFAULT_IMG_CROP_HEIGHT = 750;

    public static final int DEFAULT_VIDEO_COMPRESS_WIDTH = 960;
    public static final int DEFAULT_VIDEO_COMPRESS_HEIGHT = 960;
    public static final int DEFAULT_VIDEO_COMPRESS_BITRATE = 5;

    public static final long CHAT_VIDEO_MSG_FILE_LENGTH_LIMIT = 1024 * 1024 * 10; //10M

    public static final long MIN_CUT_DURATION_VIDEO = 3 * 1000L; // 上传视频最小剪辑时间3s
    public static final long MAX_CUT_DURATION_VIDEO = 15 * 1000L; //上传视频最多剪辑时间15s

    /**
     * 设置扫描模式
     */
    public enum ScanMode {
        IMG, //只扫描图片
        VIDEO, //只扫描视频
        ALL,  //图片视频都扫描
    }

    /**
     * 设置扫描模式
     */
    public enum AdapterMode {
        single, //单选，指定一张图片或视频
        multiple //多选，图片列表
    }

    public static class SelectBuilder {

        ScanMode scanMode;
        AdapterMode adapterMode;
        int maxNum;
        List<MediaItem> selectedImgList;
        String videoEditedPath;
        int cropImgW;
        int cropImgH;
        int itemStyle;
        int spanCount;
        boolean needImgCrop = true;
        boolean selectForChat = false;
        VideoEditLimitInfo videoEditLimitInfo;
        int requestCode = REQ_SELECT_MEDIA;

        public SelectBuilder() {
        }

        /**
         * 设置媒体扫描模式
         */
        public SelectBuilder setScanMode(ScanMode scanMode) {
            this.scanMode = scanMode;
            return this;
        }

        /**
         * 设置图片可选择的最大数量
         * 针对选择图片(IMG模式和ALL模式下的图片选择)有效
         * 设置该值将影响两个维度：是单个图片选择还是多图选择 以及多图选择的最大数量
         * 该值不设置或设置0时，会默认是多图模式，可选最大值9
         * 该值设置为1时，会默认单图选择模式。
         */
        public SelectBuilder setMaxNumToSelect(int maxNum) {
            this.maxNum = maxNum;
            return this;
        }

        /**
         * 设置视频的宽高和码率
         */
        public SelectBuilder setVideoDetailsToSelect(VideoEditLimitInfo videoEditLimitInfo) {
            this.videoEditLimitInfo = videoEditLimitInfo;
            return this;
        }

        /**
         * 单图模式下是否需要裁剪
         * 该裁剪字段仅控制图片，视频默认为可裁剪
         * 多图选择模式下暂不支持裁剪
         * 默认值是需要，所以如果需要裁剪，可以不设置
         */
        public SelectBuilder isNeedImgCrop(boolean needImgCrop) {
            this.needImgCrop = needImgCrop;
            return this;
        }

        /**
         * 设置已选图片列表，
         * 用于与为选择图片或视频做显示上的差别
         */
        public SelectBuilder setSelectedImgList(List<MediaItem> selectedImgList) {
            this.selectedImgList = selectedImgList;
            return this;
        }

        /**
         * 指定页面跳转的请求码
         * 如果没有设置，默认是:REQ_SELECT_MEDIA
         */
        public SelectBuilder setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * 设置裁剪所选图片时的所需宽高
         * 仅针对单图选择模式，多图选择模式和视频模式会忽略此参数
         * 目前仅支持宽高同值
         */
        public SelectBuilder setImgCropWH(int cropImgW, int cropImgH) {
            this.cropImgW = cropImgW;
            this.cropImgH = cropImgH;
            return this;
        }

        /**
         * 是否为聊天所选择媒体
         * 如果是，在媒体列表页面选择时
         * 需要计算视频媒体文件大小
         * 默认不是
         */
        public SelectBuilder isSelectForChat(boolean selectForChat) {
            this.selectForChat = selectForChat;
            return this;
        }

        /**
         * 设置媒体选择列表Adapter ItemView
         * 的展示样式模版
         * 展示样式模版统一包括：
         * Item未选状态
         * Item选中状态
         * Item已选状态
         */
        public SelectBuilder setAdapterItemStyle(int itemStyle) {
            this.itemStyle = itemStyle;
            return this;
        }

        /**
         * 设置媒体选择列表Adapter展示几列
         * 列的个数
         */
        public SelectBuilder setAdapterSpan(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        /**
         * 设置视频编辑后保存的文件位置
         * 针对选择视频(VIDEO模式和ALL模式下的视频选择)有效
         * 选定视频后会自动进入编辑(裁剪和压缩)页面
         * 裁剪：视频超过15秒的视频支持裁剪到15秒内
         * 压缩：视频将被压缩至540p(960*540)
         * 这两个值现在还不支持修改
         */
        public SelectBuilder setVideoEditedPath(String videoEditedPath) {
            this.videoEditedPath = videoEditedPath;
            return this;
        }

        public void openActivity(Activity context) {
            if (context == null) {
                ToastUtils.showToast("参数错误！请设置调用的上下文对象");
                return;
            }

            if (handleConfig()) {
                openUI(context, null, null);
            }
        }

        public void openActivity(Fragment fragment) {
            if (fragment == null) {
                ToastUtils.showToast("参数错误！请设置调用的fragment对象");
                return;
            }
            if (handleConfig()) {
                openUI(null, fragment, null);
            }
        }

        public void openThisActivity(Activity context, Class clazz) {
            if (context == null) {
                ToastUtils.showToast("参数错误！请设置调用的上下文对象");
                return;
            }


            if (clazz == null) {
                ToastUtils.showToast("参数错误！请设置跳转的页面");
                return;
            }

            if (handleConfig()) {
                openUI(context, null, clazz);
            }

        }

        public void openThisActivity(Fragment fragment, Class clazz) {
            if (fragment == null) {
                ToastUtils.showToast("参数错误！请设置调用的fragment对象");
                return;
            }

            if (clazz == null) {
                ToastUtils.showToast("参数错误！请设置跳转的页面");
                return;
            }

            if (handleConfig()) {
                openUI(null, fragment, clazz);
            }
        }

        private void openUI(Activity context, Fragment fragment, Class clazz) {
            if (fragment != null) {
                startActivityForResult(null, clazz, fragment, scanMode, videoEditLimitInfo, maxNum, needImgCrop, selectForChat,
                        selectedImgList, videoEditedPath, cropImgW, cropImgH, adapterMode, spanCount, itemStyle, requestCode);
            } else {
                startActivityForResult(context, clazz, null, scanMode, videoEditLimitInfo, maxNum, needImgCrop, selectForChat,
                        selectedImgList, videoEditedPath, cropImgW, cropImgH, adapterMode, spanCount, itemStyle, requestCode);
            }
        }


        private boolean handleConfig() {

            if (scanMode == null) {
                ToastUtils.showToast("媒体选择模式未设置！请调用setScanMode()方法");
                return false;
            }

            switch (scanMode) {
                case IMG:
                case ALL:
                    if (maxNum < 1) {
                        maxNum = MAX_SELECT_MEDIA_NUM_DEFAULT;
                    }

                    //为聊天的选择媒体强制只能选择1个
                    if (maxNum != 1 && selectForChat) {
                        maxNum = 1;
                    }

                    if (maxNum > 1) {
                        adapterMode = AdapterMode.multiple;
                    } else {
                        adapterMode = AdapterMode.single;
                    }
                    break;
                case VIDEO:
                    maxNum = 1;
                    adapterMode = AdapterMode.single;
                    break;
                default:

                    break;
            }

            // 多选情况下，图片的选择不支持裁剪
            if (scanMode != ScanMode.VIDEO && maxNum > 1) {
                needImgCrop = false;
            }

            return true;
        }
    }

    /**
     * 请不要直接调用此方法！
     * 请使用{@link SelectBuilder}构建参数跳转到此页面
     *
     * @param context         上下文
     * @param scanMode        媒体扫描模式
     * @param maxSelectNum    可选中媒体最大数
     * @param selectedImgList 已选中的媒体列表
     * @param mediaSavePath   视频编辑后保存的路径
     * @param cropImgW        单图选择模式时裁剪图片的所需宽
     * @param requestCode     请求码
     */
    private static void startActivityForResult(Context context,
                                               Class clazz,
                                               Fragment fragment,
                                               ScanMode scanMode,
                                               VideoEditLimitInfo videoEditLimitInfo,
                                               int maxSelectNum,
                                               boolean needImgCrop,
                                               boolean selectForChat,
                                               List<MediaItem> selectedImgList,
                                               String mediaSavePath,
                                               int cropImgW,
                                               int cropImgH,
                                               AdapterMode adapterMode,
                                               int spanCount,
                                               int itemStyle,
                                               int requestCode) {
        Intent intent = null;
        if (context != null) {
            if (clazz != null) {
                intent = new Intent(context, clazz);
            } else {
                intent = new Intent(context, DefaultMediaSelectActivity.class);
            }
        } else if (fragment != null) {
            if (clazz != null) {
                intent = new Intent(fragment.getActivity(), clazz);
            } else {
                intent = new Intent(fragment.getActivity(), DefaultMediaSelectActivity.class);
            }
        }

        if (intent == null) {
            return;
        }

        intent.putExtra(KEY_SELECT_MEDIA_SCAN_MODE, scanMode);

        intent.putExtra(KEY_SELECT_MEDIA_ADAPTER_MODE, adapterMode);

        intent.putExtra(KEY_SELECT_MEDIA_VIDEO_DETAILS, videoEditLimitInfo);

        intent.putExtra(KEY_SELECT_MEDIA_MAX_NUM, maxSelectNum);

        intent.putExtra(KEY_SELECT_MEDIA_ITEM_STYLE, itemStyle);

        intent.putExtra(KEY_SELECT_MEDIA_SPAN_COUNT, spanCount);

        intent.putExtra(KEY_SELECT_MEDIA_IMG_NEED_CROP, needImgCrop);

        intent.putExtra(KEY_SELECT_MEDIA_FOR_CHAT, selectForChat);

        if (CollectionUtils.isNotEmptyList(selectedImgList)) {
            intent.putExtra(KEY_LIST_MEDIA, (Serializable) selectedImgList);
        }

        intent.putExtra(KEY_SELECT_MEDIA_SAVE_PATH, mediaSavePath);

        intent.putExtra(KEY_SELECT_IMG_CROP_W, cropImgW);
        intent.putExtra(KEY_SELECT_IMG_CROP_H, cropImgH);

        if (context != null) {
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent, requestCode);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } else {
            fragment.startActivityForResult(intent, requestCode);
        }
    }
}
