package com.seanutf.cmmonui.widget.magictablayout;

import android.content.Context;
import android.view.Gravity;


public class MyPagerTitleView extends ScaleTransitionPagerTitleView {
    TitleSelectListener l;

    public MyPagerTitleView(Context context) {
        super(context);
        setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        if (l != null) {
            l.select(index);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        if (l != null) {
            l.deselect(index);
        }
    }

    public void setOnSelectListener(TitleSelectListener l) {
        this.l = l;
    }

    public interface TitleSelectListener {
        void select(int index);

        void deselect(int index);
    }
}
