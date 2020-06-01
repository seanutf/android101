package com.seanutf.cmmonui.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

/**
 * Created by Mustafa on 6/5/2017.
 * <p>
 * 支持WebView内部横向滑动的ViewPager
 */

public class WebHorizontalScrollViewPager extends ViewPager {
    public static boolean isPagingEnabled = true;

    public WebHorizontalScrollViewPager(Context context) {
        this(context, null);
    }

    public WebHorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static void setPagingEnabled(boolean b) {
        isPagingEnabled = b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return super.onInterceptTouchEvent(event);
        }
        try {
            return isPagingEnabled && super.onInterceptTouchEvent(event);
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
        }
        return false;

    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        return isPagingEnabled && super.canScroll(v, checkV, dx, x, y);
    }

    @Override
    public void removeView(View view) {
        super.removeView(view);
    }
}
