package com.seanutf.android.base.media.select;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.base.databinding.FragmentMediaSelectBaseBinding;
import com.seanutf.android.base.media.data.AlbumData;
import com.seanutf.android.base.media.data.MediaInfo;
import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.data.VideoEditLimitInfo;
import com.seanutf.android.base.media.select.adapter.BaseMediaSelectAdapter;
import com.seanutf.android.base.media.select.adapter.MultipleSelectAdapter;
import com.seanutf.android.base.media.select.adapter.SingleSelectAdapter;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.cmmonui.adapter.CommonRecyclerViewAdapter;
import com.seanutf.cmmonui.arch.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
import static com.seanutf.android.base.media.select.MediaSelectManager.SPAN_COUNT_GRID_DEFAULT;

/**
 * 承载媒体选择器的父类Fragment
 * 是所有媒体选择Fragment的父类
 * 本类承载标题和媒体列表，仅此而已
 * 一般情况下，开发者不需要创建新的Activity来嵌套本类实现功能
 * 开发者可直接通过MediaSelectManager调用打开默认的
 * DefaultMediaSelectActivity即可实现媒体选择功能
 * 其他场景业务需求，开发者可继承本类，实现自定义的需求，尽量不要
 * 为业务更改本类代码(除非进行优化和扩展)
 * 即，开发者如果需要实现自定义的媒体选择器，两种方式：
 * 1.创建Activity，嵌套BaseMediaSelectFragment，寻求实现效果
 * 2.创建Activity，嵌套继承自BaseMediaSelectFragment，Fragment自定义功能，，寻求实现效果
 */
public class BaseMediaSelectFragment extends BaseFragment<BaseMediaSelectViewModel> {

    public static final String TAG = BaseMediaSelectFragment.class.getSimpleName();

    FragmentMediaSelectBaseBinding vb;

    private BaseMediaSelectAdapter mediaSelectAdapter;

    private ProgressDialog mProgressDialog;

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
    private MediaSelectManager.AdapterMode adapterMode;
    private BaseMediaSelectAdapter.OnClickMediaListener clickMediaListener;
    private MediaListAdapterListener adapterListener = new MediaListAdapterListener();
    private MediaListObserver listObserver = new MediaListObserver();
    private boolean isLoadNew = true;


    public static BaseMediaSelectFragment newInstance() {
        return new BaseMediaSelectFragment();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        // 确认容器 Activity 已实现该回调接口。否则，抛出异常
        try {
            clickMediaListener = (BaseMediaSelectAdapter.OnClickMediaListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BaseMediaSelectAdapter.OnClickMediaListener");
        }
    }

    @Override
    protected View getContentLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vb = FragmentMediaSelectBaseBinding.inflate(inflater);
        return vb.getRoot();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            scanMode = (MediaSelectManager.ScanMode) bundle.getSerializable(KEY_SELECT_MEDIA_SCAN_MODE);
            adapterMode = (MediaSelectManager.AdapterMode) bundle.getSerializable(KEY_SELECT_MEDIA_ADAPTER_MODE);
            videoEditLimitInfo = (VideoEditLimitInfo) bundle.getSerializable(KEY_SELECT_MEDIA_VIDEO_DETAILS);
            //adapter关注数据
            selectPhotoMaxNum = bundle.getInt(KEY_SELECT_MEDIA_MAX_NUM, MediaSelectManager.MAX_SELECT_MEDIA_NUM_DEFAULT);
            selectedImgList = (List<MediaItem>) bundle.getSerializable(KEY_LIST_MEDIA);
            mediaSavePath = bundle.getString(KEY_SELECT_MEDIA_SAVE_PATH);
            needImgCrop = bundle.getBoolean(KEY_SELECT_MEDIA_IMG_NEED_CROP, true);
            selectForChat = bundle.getBoolean(KEY_SELECT_MEDIA_FOR_CHAT, false);
            itemStyle = bundle.getInt(KEY_SELECT_MEDIA_ITEM_STYLE, 0);
            spanCount = bundle.getInt(KEY_SELECT_MEDIA_SPAN_COUNT, SPAN_COUNT_GRID_DEFAULT);

            cropImgW = bundle.getInt(KEY_SELECT_IMG_CROP_W, DEFAULT_IMG_CROP_WIDTH);
            cropImgH = bundle.getInt(KEY_SELECT_IMG_CROP_H, DEFAULT_IMG_CROP_HEIGHT);
        }
    }

    public void refreshData() {
        //showProgress(false);
        loadNewData();
    }

    private void loadNewData() {
        isLoadNew = true;
        viewModel.getMediaData().observe(getActivity(), listObserver);
    }


    public List<MediaItem> getCurrentMediaList() {
        return mediaSelectAdapter.getCurrentMediaList();
    }

    final class MediaListAdapterListener implements CommonRecyclerViewAdapter.CommonAdapterListener {
        @Override
        public void onLoadMore() {
            loadMoreData();
        }

        @Override
        public void onRetry() {

        }

        @Override
        public void onClickLoadMore() {

        }
    }

    private void loadMoreData() {
        isLoadNew = false;
        viewModel.getMediaData().observe(getActivity(), listObserver);
    }

    final class MediaListObserver implements Observer<MediaInfo> {
        @Override
        public void onChanged(MediaInfo mediaInfo) {
            //todo
            //dismissProgress();
            if (mediaInfo != null) {

                setAlbumData(mediaInfo.albumList);
                switch (scanMode) {
                    case IMG:
                        if (CollectionUtils.isNotEmptyList(mediaInfo.imageList)) {
                            mediaSelectAdapter.setData(isLoadNew, false, (ArrayList) mediaInfo.imageList);
                        }
                        break;
                    case VIDEO:
                        if (CollectionUtils.isNotEmptyList(mediaInfo.allVideoList)) {
                            mediaSelectAdapter.setData(isLoadNew, false, (ArrayList) mediaInfo.allVideoList);
                        }
                        break;
                    case ALL:
                        if (CollectionUtils.isNotEmptyList(mediaInfo.imageVideoList)) {
                            mediaSelectAdapter.setData(isLoadNew, false, (ArrayList) mediaInfo.imageVideoList);
                        }
                        break;
                }

            }
        }
    }

    public List<MediaItem> getSelectedImageList() {
        return mediaSelectAdapter.getSelectedImageList();
    }

    private void setAlbumData(List<AlbumData> albumList) {
        viewModel.setAlbumList(albumList);
    }

    @Override
    protected void handle() {
        initData();
        viewModel.setConfig(scanMode, adapterMode, videoEditLimitInfo, selectPhotoMaxNum,
                selectedImgList, mediaSavePath, needImgCrop, selectForChat, cropImgW, cropImgH);
        vb.rvMediaList.setLayoutManager(new GridLayoutManager(getActivity(), spanCount, RecyclerView.VERTICAL, false));

        switch (adapterMode) {
            case single:
                mediaSelectAdapter = new SingleSelectAdapter(getActivity(), clickMediaListener,
                        adapterListener);
                break;
            case multiple:
                mediaSelectAdapter = new MultipleSelectAdapter(getActivity(), clickMediaListener,
                        adapterListener);
                break;
        }

        mediaSelectAdapter.isNeedFooterView(false);
        mediaSelectAdapter.setConfig(scanMode, adapterMode, videoEditLimitInfo, selectPhotoMaxNum,
                selectedImgList, mediaSavePath, needImgCrop, selectForChat, itemStyle,
                spanCount, cropImgW, cropImgH);
        vb.rvMediaList.setAdapter(mediaSelectAdapter);

        refreshData();
    }

    @Override
    protected BaseMediaSelectViewModel generateViewModel() {
        return new ViewModelProvider(getActivity()).get(BaseMediaSelectViewModel.class);
    }

    @Override
    protected void setViews(@Nullable Bundle savedInstanceState) {

    }
}
