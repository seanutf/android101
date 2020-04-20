package com.seanutf.android.homewiki.post;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.seanutf.android.base.homwiki.data.Tag;
import com.seanutf.android.base.homwiki.data.WikiData;
import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.base.media.select.MediaSelectManager;
import com.seanutf.android.commonutil.util.AppSettings;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.android.commonutil.util.StringUtil;
import com.seanutf.android.databinding.ActivityHomeWikiPostBinding;
import com.seanutf.android.homewiki.HomeWikiManager;
import com.seanutf.cmmonui.arch.BaseActivity;
import com.seanutf.cmmonui.util.ToastUtils;

import java.util.List;

import static com.seanutf.android.base.IntentKeyUtil.KEY_LIST_MEDIA;
import static com.seanutf.android.base.RequestAndResultCodeUtil.REQ_SELECT_MEDIA;

public class HomeWikiPostActivity extends BaseActivity {

    ActivityHomeWikiPostBinding vb;
    List<MediaItem> mediaItemList;
    List<Tag> tagList;

    public static void startActivity(BaseActivity activity) {
        Intent intent = new Intent(activity, HomeWikiPostActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vb = ActivityHomeWikiPostBinding.inflate(getLayoutInflater());
        setContentView(vb.getRoot());
        setViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_SELECT_MEDIA:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        List<MediaItem> list = (List<MediaItem>) data.getSerializableExtra(KEY_LIST_MEDIA);
                        mediaItemList = list;
                    }
                }
                break;
        }
    }

    private void setViews() {
        vb.tvBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPost();
            }
        });

        vb.tvBtnAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMedia();
            }
        });

        vb.tvTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeWikiTagListActivity.startActivity(HomeWikiPostActivity.this);
            }
        });
    }

    private void checkPost() {
        String postContent = vb.etPost.getText().toString().trim();
        if (!StringUtil.isNotEmptyString(postContent) && CollectionUtils.isEmptyList(mediaItemList)) {
            ToastUtils.showToast("您还没有输入内容");
            return;
        }

        toPostContent(postContent);
    }

    private void toPostContent(String postContent) {
        String postTitle = vb.etPostTitle.getText().toString().trim();

        WikiData data = new WikiData();
        data.content = postContent;
        data.title = postTitle;
        data.tagList = tagList;
        int sortNum = AppSettings.getInstance().getWikiPostSort();
        data.sortId = sortNum;
        AppSettings.getInstance().setWikiPostSort(sortNum + 1);

        //todo 替换
        HomeWikiManager.getInstance().savePost(data);
    }

    private void selectMedia() {
        MediaSelectManager.SelectBuilder builder = new MediaSelectManager.SelectBuilder();
        builder.isNeedImgCrop(false).isSelectForChat(false)
                .setAdapterSpan(3)
                .setImgCropWH(750, 750)
                .setScanMode(MediaSelectManager.ScanMode.IMG)
                .setMaxNumToSelect(9)
                .setAdapterItemStyle(0)
                .setSelectedImgList(null)
                .openActivity(this);
    }
}
