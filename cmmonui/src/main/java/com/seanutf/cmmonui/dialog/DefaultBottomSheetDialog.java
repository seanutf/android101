package com.seanutf.cmmonui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.seanutf.cmmonui.util.DensityUtil;

/**
 * Created by sean 2019/4/11.
 */
public class DefaultBottomSheetDialog extends BottomSheetDialog {

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet,
                                   @BottomSheetBehavior.State int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
                BottomSheetBehavior.from(bottomSheet).setState(
                        BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
    private int mPeekHeight = (int) DensityUtil.getScreenHeight();
    private int mMaxHeight = (int) DensityUtil.getScreenHeight();
    private boolean mCreated;
    private Window mWindow;
    private BottomSheetBehavior mBottomSheetBehavior;

    public DefaultBottomSheetDialog(@NonNull Context context, int peekHeight, int maxHeight) {
        this(context);
        mPeekHeight = peekHeight;
        mMaxHeight = maxHeight;
    }


    public DefaultBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public DefaultBottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    public DefaultBottomSheetDialog(@NonNull Context context, boolean cancelable,
                                    OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWindow = getWindow();

        mCreated = true;

        setPeekHeight();
        setMaxHeight();
        setBottomSheetCallback();

        setCanceledOnTouchOutside(true);
    }

    public void setPeekHeight(int peekHeight) {
        mPeekHeight = peekHeight;

        if (mCreated) {
            setPeekHeight();
        }
    }

    public void setMaxHeight(int height) {
        mMaxHeight = height;

        if (mCreated) {
            setMaxHeight();
        }
    }

    public void setBatterSwipeDismiss(boolean enabled) {
        if (enabled) {

        }
    }

    private void setPeekHeight() {
        if (mPeekHeight <= 0) {
            return;
        }

        if (getBottomSheetBehavior() != null) {
            mBottomSheetBehavior.setPeekHeight(mPeekHeight);
        }
    }

    private void setMaxHeight() {
        if (mMaxHeight <= 0) {
            return;
        }

        mWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, mMaxHeight);
        mWindow.setGravity(Gravity.BOTTOM);
    }

    private BottomSheetBehavior getBottomSheetBehavior() {
        if (mBottomSheetBehavior != null) {
            return mBottomSheetBehavior;
        }

        View view = mWindow.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        view.setBackgroundResource(android.R.color.transparent);
        // setContentView() 没有调用
        if (view == null) {
            return null;
        }
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        return mBottomSheetBehavior;
    }

    private void setBottomSheetCallback() {
        if (getBottomSheetBehavior() != null) {
            //            mBottomSheetBehavior.setBottomSheetCallback(mBottomSheetCallback);
            //按产品要求，该dialog无需实现下拉隐藏功能，故将其屏蔽掉，以后再使用dialog如无特殊需求，可直接实现简单dialog效果
            mBottomSheetBehavior.setHideable(false);
        }
    }
}