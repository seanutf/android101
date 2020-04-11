package com.seanutf.cmmonui.widget.magictablayout;

import android.content.Context;


public class ScaleImgPagerTitleView extends SimpleImgTitleView {
    private float mMinScale = 0.74f;

    public ScaleImgPagerTitleView(Context context) {
        super(context);
    }

    public ScaleImgPagerTitleView(Context context, int height) {
        super(context, height);
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        super.onEnter(index, totalCount, enterPercent, leftToRight);    // 实现颜色渐变
        setScaleX(mMinScale + (1.0f - mMinScale) * enterPercent);
        setScaleY(mMinScale + (1.0f - mMinScale) * enterPercent);
        setAlpha(mMinScale + (1.0f - mMinScale) * enterPercent);
        //
        iv.setY(((iv.getHeight() * (1.0f - mMinScale) * (1 - enterPercent)) / 2 + iv.getTop()));
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        super.onLeave(index, totalCount, leavePercent, leftToRight);    // 实现颜色渐变
        setScaleX(1.0f + (mMinScale - 1.0f) * leavePercent);
        setScaleY(1.0f + (mMinScale - 1.0f) * leavePercent);
        setAlpha(1.0f + (mMinScale - 1.0f) * leavePercent);
        iv.setY(((iv.getHeight() * (1.0f - mMinScale) * leavePercent) / 2 + iv.getTop()));
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);

    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        setAlpha(0.6f);
    }

    public float getMinScale() {
        return mMinScale;
    }

    public void setMinScale(float minScale) {
        mMinScale = minScale;
    }
}
