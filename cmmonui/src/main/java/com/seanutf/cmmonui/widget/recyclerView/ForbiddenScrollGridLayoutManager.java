package com.seanutf.cmmonui.widget.recyclerView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.GridLayoutManager;

/**
 * 在Recyclerview中禁止滑动的GridLayoutManager
 * https://www.jianshu.com/p/49bf92baa98b
 */
public class ForbiddenScrollGridLayoutManager extends GridLayoutManager {

    private boolean isScrollEnabled = true;

    public ForbiddenScrollGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public ForbiddenScrollGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ForbiddenScrollGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled;
    }
}
