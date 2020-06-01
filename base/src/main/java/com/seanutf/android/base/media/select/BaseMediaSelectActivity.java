package com.seanutf.android.base.media.select;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.fragment.app.FragmentTransaction;

import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.data.VideoEditLimitInfo;
import com.seanutf.android.base.media.select.adapter.BaseMediaSelectAdapter;
import com.seanutf.cmmonui.arch.BaseActivity;

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
import static com.seanutf.android.base.media.select.MediaSelectManager.DEFAULT_IMG_CROP_HEIGHT;
import static com.seanutf.android.base.media.select.MediaSelectManager.DEFAULT_IMG_CROP_WIDTH;
import static com.seanutf.android.base.media.select.MediaSelectManager.DEFAULT_VIDEO_COMPRESS_BITRATE;
import static com.seanutf.android.base.media.select.MediaSelectManager.DEFAULT_VIDEO_COMPRESS_HEIGHT;
import static com.seanutf.android.base.media.select.MediaSelectManager.DEFAULT_VIDEO_COMPRESS_WIDTH;
import static com.seanutf.android.base.media.select.MediaSelectManager.SPAN_COUNT_GRID_DEFAULT;

public abstract class BaseMediaSelectActivity extends BaseActivity implements BaseMediaSelectAdapter.OnClickMediaListener {

    protected BaseMediaSelectFragment selectFragment;

    //配置数据
    protected MediaSelectManager.ScanMode scanMode;
    protected VideoEditLimitInfo videoEditLimitInfo;
    protected String mediaSavePath; //命名为了扩展性，目前用于视频编辑后保存的位置
    protected boolean needImgCrop;
    protected boolean selectForChat;
    protected int selectPhotoMaxNum;
    protected List<MediaItem> selectedImgList;
    protected int cropImgW;
    protected int cropImgH;
    protected int itemStyle;
    protected int spanCount;
    protected MediaSelectManager.AdapterMode adapterMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initConfigData();
    }

    @Override
    public void onBackPressed() {
        onClickBackBtn();
    }

    /**
     * 获取外界传递的配置数据，子类可以扩展处理
     * 并且会在Activity执行onCreate()方法时调用
     */
    @CallSuper
    protected void initConfigData() {
        scanMode = (MediaSelectManager.ScanMode) getIntent().getSerializableExtra(KEY_SELECT_MEDIA_SCAN_MODE);
        adapterMode = (MediaSelectManager.AdapterMode) getIntent().getSerializableExtra(KEY_SELECT_MEDIA_ADAPTER_MODE);
        videoEditLimitInfo = (VideoEditLimitInfo) getIntent().getSerializableExtra(KEY_SELECT_MEDIA_VIDEO_DETAILS);
        //adapter关注数据
        selectPhotoMaxNum = getIntent().getIntExtra(KEY_SELECT_MEDIA_MAX_NUM, MediaSelectManager.MAX_SELECT_MEDIA_NUM_DEFAULT);
        selectedImgList = (List<MediaItem>) getIntent().getSerializableExtra(KEY_LIST_MEDIA);
        mediaSavePath = getIntent().getStringExtra(KEY_SELECT_MEDIA_SAVE_PATH);
        needImgCrop = getIntent().getBooleanExtra(KEY_SELECT_MEDIA_IMG_NEED_CROP, true);
        selectForChat = getIntent().getBooleanExtra(KEY_SELECT_MEDIA_FOR_CHAT, false);
        itemStyle = getIntent().getIntExtra(KEY_SELECT_MEDIA_ITEM_STYLE, 0);
        spanCount = getIntent().getIntExtra(KEY_SELECT_MEDIA_SPAN_COUNT, SPAN_COUNT_GRID_DEFAULT);

        if (videoEditLimitInfo == null &&
                scanMode != MediaSelectManager.ScanMode.IMG) {
            videoEditLimitInfo = new VideoEditLimitInfo(DEFAULT_VIDEO_COMPRESS_WIDTH, DEFAULT_VIDEO_COMPRESS_HEIGHT, DEFAULT_VIDEO_COMPRESS_BITRATE);
        }

        cropImgW = getIntent().getIntExtra(KEY_SELECT_IMG_CROP_W, DEFAULT_IMG_CROP_WIDTH);
        cropImgH = getIntent().getIntExtra(KEY_SELECT_IMG_CROP_H, DEFAULT_IMG_CROP_HEIGHT);
    }

    /**
     * 添加媒体选择默认的Fragment
     * 当本类子类有自定义的Fragment需要添加时，
     * 可以重写本方法
     */
    protected void addFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        selectFragment = (BaseMediaSelectFragment) getSupportFragmentManager().findFragmentByTag(BaseMediaSelectFragment.TAG);

        if (selectFragment == null) {
            selectFragment = BaseMediaSelectFragment.newInstance();
        }

        if (selectFragment.isAdded()) {
            transaction.show(selectFragment);
        } else {
            transaction.add(getContentId(), selectFragment, BaseMediaSelectFragment.TAG);
        }

        selectFragment.setArguments(getIntent().getExtras());

        transaction.commitAllowingStateLoss();
    }

    /**
     * 点击返回键或返回按钮时的逻辑
     */
    protected abstract void onClickBackBtn();

    /**
     * 点击当前媒体目录按钮时的逻辑
     */
    protected abstract void onClickAlbumDir();

    /**
     * 点击下一步按钮时的逻辑
     */
    protected abstract void onClickNextStep();

    /**
     * 点击"确定"按钮时的逻辑
     */
    protected abstract void onClickSure();

    /**
     * 获取Fragment容器的ID
     */
    protected abstract int getContentId();

}
