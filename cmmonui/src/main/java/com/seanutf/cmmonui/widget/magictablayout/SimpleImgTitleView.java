package com.seanutf.cmmonui.widget.magictablayout;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.seanutf.cmmonui.util.DensityUtil;


/**
 * 带文本的指示器标题
 * 博客: http://hackware.lucode.net
 * Created by hackware on 2016/6/26.
 */
public class SimpleImgTitleView extends FrameLayout implements IMeasurablePagerTitleView {
    public ImageView iv;
    protected int mSelectedColor;
    protected int mNormalColor;
    private int height;

    public SimpleImgTitleView(Context context, int height) {
        super(context, null);
        this.height = height;
        init(context);
    }

    public SimpleImgTitleView(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        iv = new ImageView(context);
        iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        if (height > 0)
            lp.height = height;
        iv.setLayoutParams(lp);
        iv.setPadding(0, 0, 0, DensityUtil.dp2px(1));
        //
        iv.setBackground(null);
        int padding = DensityUtil.dp2px(3);
        setPadding(padding, 0, padding, 0);
        addView(iv);
    }

    @Override
    public void onSelected(int index, int totalCount) {

    }

    @Override
    public void onDeselected(int index, int totalCount) {

    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override
    public int getContentLeft() {
        return 0;
    }

    @Override
    public int getContentTop() {
        return 0;
    }

    @Override
    public int getContentRight() {
        return 0;
    }

    @Override
    public int getContentBottom() {
        return 0;
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }
}
