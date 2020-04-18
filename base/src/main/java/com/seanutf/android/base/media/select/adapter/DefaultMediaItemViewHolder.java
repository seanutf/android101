package com.seanutf.android.base.media.select.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.base.R;
import com.seanutf.cmmonui.widget.imageview.SquareImageView;

/**
 * 改布局的时候需要注意，这是媒体选择器框架中
 * Item样式中的一种
 * 适用于单选和多选
 */
public class DefaultMediaItemViewHolder extends RecyclerView.ViewHolder {
    SquareImageView ivPhoto;
    TextView tvVideoDuration;
    TextView tvSelectNum;
    View videoTag;
    LinearLayout llSelectNum;
    SquareImageView unableSelectMask;

    public DefaultMediaItemViewHolder(@NonNull View itemView) {
        super(itemView);
        ivPhoto = itemView.findViewById(R.id.ivPhoto);
        tvVideoDuration = itemView.findViewById(R.id.tvVideoDuration);
        tvSelectNum = itemView.findViewById(R.id.tvSelectNum);
        videoTag = itemView.findViewById(R.id.videoTag);
        llSelectNum = itemView.findViewById(R.id.llSelectNum);
        unableSelectMask = itemView.findViewById(R.id.ivUnSelect);
    }
}
