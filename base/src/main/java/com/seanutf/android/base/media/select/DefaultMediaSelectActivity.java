package com.seanutf.android.base.media.select;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.seanutf.android.base.R;
import com.seanutf.android.base.databinding.ActivityMediaSelectDefaultBinding;
import com.seanutf.android.base.databinding.LayoutMediaAlbumBinding;
import com.seanutf.android.base.media.data.AlbumData;
import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.select.adapter.MediaAlbumAdapter;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.android.commonutil.util.StringUtil;
import com.seanutf.cmmonui.dialog.OptionSheetDialog;
import com.seanutf.cmmonui.util.DensityUtil;
import com.seanutf.cmmonui.util.ToastUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.seanutf.android.base.IntentKeyUtil.KEY_LIST_MEDIA;
import static com.seanutf.android.base.IntentKeyUtil.KEY_MEDIA_DATA;
import static com.seanutf.android.base.RequestAndResultCodeUtil.REQ_MEDIA_IMG_CROP;
import static com.seanutf.android.base.RequestAndResultCodeUtil.REQ_MEDIA_VIDEO_EDIT;
import static com.seanutf.android.base.media.select.MediaSelectManager.CHAT_VIDEO_MSG_FILE_LENGTH_LIMIT;

/**
 * 应用内选择媒体框架默认打开的Activity
 * 一般情况下，开发者可以通过MediaSelectManager调用本页面，
 * 开发者也可以根据实际业务需求创建新的Activity，实现相关方法，即可
 * 得到和业务相定制的媒体选择页面
 * 同时，一般情况下，开发者所创建的Activity嵌套BaseMediaSelectFragment即可
 * 如果BaseMediaSelectFragment不能满足业务需求，可自定义Fragment继承自
 * BaseMediaSelectFragment，实现业务需求，而尽量少更改BaseMediaSelectFragment
 * 即，开发者如果需要实现自定义的媒体选择器，两种方式：
 * 1.创建Activity，嵌套BaseMediaSelectFragment，寻求实现效果
 * 2.创建Activity，嵌套继承自BaseMediaSelectFragment，Fragment自定义功能，，寻求实现效果
 */
public class DefaultMediaSelectActivity extends BaseMediaSelectActivity {

    ActivityMediaSelectDefaultBinding vb;
    BaseMediaSelectViewModel viewModel;

    private PopupWindow popupWindow;
    private MediaAlbumAdapter albumAdapter;

    private int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityMediaSelectDefaultBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        viewModel = new ViewModelProvider(this).get(BaseMediaSelectViewModel.class);
        setViews();
        addFragment();
    }

    @Override
    protected void initConfigData() {
        super.initConfigData();
        mScreenHeight = (int) DensityUtil.getScreenHeight();
    }

    @Override
    protected int getContentId() {
        return vb.flFragmentContent.getId();
    }

    private void setViews() {
        vb.llTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAlbumDir();
            }
        });

        vb.tvBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNextStep();
            }
        });

        vb.tvBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackBtn();
            }
        });

        isShowPopup(false);

        if (adapterMode == MediaSelectManager.AdapterMode.single) {
            vb.tvBtnNext.setVisibility(View.INVISIBLE);
        }

        switch (scanMode) {
            case VIDEO:
                vb.tvAlbumName.setText("所有视频");
                break;
            case IMG:
                vb.tvAlbumName.setText("所有图片");
                break;
            case ALL:
                vb.tvAlbumName.setText("图片和视频");
                break;
        }
    }

    public void returnMediaListToFinish(List<MediaItem> mediaList) {
        Intent intent = new Intent();
        intent.putExtra(KEY_LIST_MEDIA, (Serializable) mediaList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onClickBackBtn() {
        //这里和完成按钮的点击有些区别：取消按钮的判断依据是本次的选择集合，完成按钮的判断依据是总体的选择集合
        List<MediaItem> resultList = new ArrayList<>();
        List<MediaItem> currentMediaList = selectFragment.getCurrentMediaList();
        List<MediaItem> selectedMediaList = viewModel.getSelectedImageList();
        if (CollectionUtils.isNotEmptyList(currentMediaList)) {
            List<String> optionList = new ArrayList<>();
            optionList.add(getResources().getString(R.string.text_save_exit));
            optionList.add(getResources().getString(R.string.text_clear_exit));
            optionList.add(getResources().getString(R.string.app_cancel));
            final OptionSheetDialog sheetDialog = new OptionSheetDialog(this, optionList);
            sheetDialog.setListener(index -> {
                if (0 == index) {
                    sheetDialog.dismiss();
                    if (CollectionUtils.isNotEmptyList(selectedMediaList)) {
                        resultList.addAll(selectedMediaList);
                    }
                    for (MediaItem item : currentMediaList) {
                        if (item != null) {
                            item.isFirstSelect = true;
                        }
                    }
                    resultList.addAll(currentMediaList);
                    returnMediaListToFinish(resultList);
                    finish();
                } else if (1 == index) {
                    finish();
                } else if (2 == index) {
                    sheetDialog.dismiss();
                }
            }).setColorList(Color.RED, 1).show();
        } else {
            finish();
        }
    }

    @Override
    protected void onClickAlbumDir() {
        popupAlbum();
    }

    @Override
    protected void onClickNextStep() {
        //因为单图选择模式和视频模式都是点选后跳转到下页面，所以这里一定是多图模式
        List<MediaItem> resultList = new ArrayList<>();

        List<MediaItem> currentMediaList = selectFragment.getCurrentMediaList();

        List<MediaItem> selectedMediaList = viewModel.getSelectedImageList();

        if (CollectionUtils.isNotEmptyList(selectedMediaList)) {
            resultList.addAll(selectedMediaList);
        }

        for (MediaItem item : currentMediaList) {
            if (item != null) {
                item.isFirstSelect = true;
            }
        }

        if (CollectionUtils.isNotEmptyList(currentMediaList)) {
            resultList.addAll(currentMediaList);
        }


        if (CollectionUtils.isNotEmptyList(resultList)) {
            returnMediaListToFinish(resultList);
        } else {
            ToastUtils.showToast("请至少选择一张图片");
        }
    }

    @Override
    protected void onClickSure() {

    }

    @Override
    public void onClickImg(boolean isSelect) {

    }

    @Override
    public void onClickVideo(MediaItem mediaItem) {
        handleVideo(mediaItem);
    }

    private void handleVideo(MediaItem mediaItem) {
        if (selectForChat) {
            if (StringUtil.isNotEmptyString(mediaItem.mediaPath)) {
                File videoFile = new File(mediaItem.mediaPath);
                if (videoFile.exists()) {
                    if (mediaItem.duration <= MediaSelectManager.MAX_CUT_DURATION_VIDEO &&
                            videoFile.length() <= (CHAT_VIDEO_MSG_FILE_LENGTH_LIMIT)) {
                        //只有时长小于15秒（时长控制是业务逻辑）并且文件大小在10M以内（文件大小是环信控制）的mp4文件才可发送。
                        mediaItem.processPath = mediaItem.mediaPath;
                        returnMediaToFinish(mediaItem);
                    } else {
                        if (mediaItem.duration > MediaSelectManager.MAX_CUT_DURATION_VIDEO) {
                            //todo 裁剪且压缩
                            //                            VideoEditActivity.startActivity(this, mediaItem.mediaPath,
                            //                                    FileUtils.getChatVideoCompressionDir(), videoEditLimitInfo, mediaItem.duration,
                            //                                    true, CHAT_VIDEO_MSG_FILE_LENGTH_LIMIT, REQ_MEDIA_VIDEO_EDIT);
                        } else if (videoFile.length() > (CHAT_VIDEO_MSG_FILE_LENGTH_LIMIT)) {
                            // 压缩
                            compressVideo(mediaItem);
                        }
                    }
                }
            }
        } else {
            //todo
            //            VideoEditActivity.startActivity(this, mediaItem.mediaPath, mediaSavePath, videoEditLimitInfo,
            //                    mediaItem.duration, false, 0, REQ_MEDIA_VIDEO_EDIT);
        }
    }

    @Override
    public void onClickImg(MediaItem mediaItem) {
        if (needImgCrop) {
            //todo
            //PhotoCutActivity.startActivity(this, mediaItem, cropImgW, REQ_MEDIA_IMG_CROP);
        } else {
            returnMediaToFinish(mediaItem);
        }
    }

    @Override
    public void onClickMedia(List<MediaItem> itemList) {
        setNextStepButton(itemList.size());
    }

    private void setNextStepButton(int size) {
        if (size > 0) {
            vb.tvBtnNext.setText("下一步(" + String.valueOf(size) + ")");
            vb.tvBtnNext.setTextColor(this.getResources().getColor(R.color.app_color_font_f95555));
        } else {
            vb.tvBtnNext.setText(R.string.app_next_step);
            vb.tvBtnNext.setTextColor(this.getResources().getColor(R.color.app_color_font_aaaaaa));
        }
    }

    private void compressVideo(MediaItem mediaItem) {
        //todo
        //        showProgress(false);
        //        MediaUtil.getInstence().compressVideoToMp4(mediaItem.mediaPath, videoEditLimitInfo.videoWith, videoEditLimitInfo.videoHight, videoEditLimitInfo.videoBitrate, new MediaUtil.OnCropVideoListener() {
        //            @Override
        //            public void onFailure(String s, String sourcePath, String targetPath, String info) {
        //                dismissProgress();
        //                ToastUtils.showToast(info);
        //            }
        //
        //            @Override
        //            public void onSuccess(String s, String sourcePath, String targetPath, String info) {
        //                dismissProgress();
        //                mediaItem.processPath = targetPath;
        //                returnMediaToFinish(mediaItem);
        //            }
        //
        //            @Override
        //            public void onProgress(String s, String sourcePath, String targetPath, String info) {
        //
        //            }
        //        });
    }

    private void popupAlbum() {
        List<AlbumData> albumData = viewModel.getAlbumList();
        if (CollectionUtils.isNotEmptyList(albumData)) {
            initListDirPopupWindow(albumData);
        } else {
            ToastUtils.showToast(R.string.toast_album_not_can_select);
        }
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindow(List<AlbumData> albumData) {
        if (popupWindow == null) {
            LayoutMediaAlbumBinding albumVb = LayoutMediaAlbumBinding.inflate(getLayoutInflater());

            popupWindow = new PopupWindow(albumVb.getRoot(), ViewGroup.LayoutParams.MATCH_PARENT, mScreenHeight * 3 / 5);
            albumAdapter = new MediaAlbumAdapter(this, new MediaAlbumAdapter.OnClickAlbumCallback() {
                @Override
                public void onClick(AlbumData albumData) {
                    if (albumData != null) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        vb.tvAlbumName.setText(albumData.getName());
                        viewModel.setCurrentAlbum(albumData.getDir());
                        selectFragment.refreshData();
                    } else {
                        ToastUtils.showToast(R.string.toast_select_alum_is_invalid);
                    }
                }
            }, albumData);
            albumVb.rvAlbum.setLayoutManager(new LinearLayoutManager(this));
            albumVb.rvAlbum.setAdapter(albumAdapter);

            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }

        isShowPopup(true);

        popupWindow.showAsDropDown(vb.llTitle, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isShowPopup(false);
            }
        });
    }

    private void isShowPopup(boolean showPopup) {
        backgroundAlpha(showPopup);
        setTitleTextImg(showPopup);
    }

    private void backgroundAlpha(boolean showPopup) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        if (showPopup) {
            lp.alpha = 0.5f; //0.0-1.0
        } else {
            lp.alpha = 1f; //0.0-1.0
        }
        getWindow().setAttributes(lp);
    }

    private void setTitleTextImg(boolean showPopup) {
        if (showPopup) {
            vb.icAlbumState.setImageResource(R.drawable.arrow_up_icon);
        } else {
            vb.icAlbumState.setImageResource(R.drawable.arrow_down_icon);
        }
    }

    public void returnMediaToFinish(MediaItem mediaItem) {
        List<MediaItem> mediaList = new ArrayList<>();
        mediaList.add(mediaItem);
        returnMediaListToFinish(mediaList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_MEDIA_VIDEO_EDIT:
                if (resultCode == RESULT_OK && data != null) {
                    MediaItem videoEditResult = (MediaItem) data.getSerializableExtra(KEY_MEDIA_DATA);
                    if (videoEditResult != null) {
                        returnMediaToFinish(videoEditResult);
                    }
                }
                break;
            case REQ_MEDIA_IMG_CROP:
                if (resultCode == RESULT_OK && data != null) {
                    MediaItem cropMedia = (MediaItem) data.getSerializableExtra(KEY_MEDIA_DATA);
                    if (cropMedia != null) {
                        returnMediaToFinish(cropMedia);
                    } else {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                }
                break;
        }
    }
}
