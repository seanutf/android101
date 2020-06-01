package com.seanutf.cmmonui.widget.recyclerView;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    int countNum;
    int lineNum;
    private int itemSpace;
    private int itemNum;

    /**
     * @param itemSpace item间隔
     * @param itemNum   每行item的个数
     */
    public RecyclerItemDecoration(int itemSpace, int itemNum, int count) {
        this.itemSpace = itemSpace;
        this.itemNum = itemNum;
        this.countNum = count;
        if (count < 5) {
            lineNum = 1;
        } else if (count < 9) {
            lineNum = 2;
        } else if (count == 9) {
            lineNum = 3;
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = itemSpace;
        outRect.top = 0;

        //outRect.left = itemSpace;
        outRect.left = 0;
        outRect.right = 0;

        if ((parent.getChildLayoutPosition(view) + 1) % itemNum == 0) {  //parent.getChildLayoutPosition(view) 获取view的下标
            //outRect.left = 0;
            outRect.right = 0;
        } else {
            outRect.right = itemSpace;
            //outRect.left = 0;
            //outRect.left = itemSpace;
        }

        int position = parent.getChildLayoutPosition(view) + 1;
        if (position <= 4) {
            if (countNum > 4) {
                outRect.bottom = itemSpace;
            } else {
                outRect.bottom = 0;
            }
        } else if (position <= 8) {
            if (countNum > 8) {
                outRect.bottom = itemSpace;
            } else {
                outRect.bottom = 0;
            }
        }
    }
}
