package com.seanutf.cmmonui.widget.recyclerView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 默认的recyclerview的加载更多listener
 */
public abstract class EndlessSlidingListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    int[] lastPositions;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //当不滑动时
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取最后一个完全显示的itemposition
                int lastItemPosition = manager.findLastVisibleItemPosition();
                int itemCount = manager.getItemCount();
                //判断是否滑动到了最后一个item, 并且是向上滑动
                if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                    //加载更多
                    onLoadMore();
                }
            }
        } else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            //Log.e("ListTest","recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager");
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            //当不滑动时
            //Log.e("ListTest","the newState is :" + newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取最后一个完全显示的itemposition
                if (lastPositions == null) {
                    lastPositions = new int[manager.getSpanCount()];
                }

                //Log.e("ListTest","the lastPositions == null:" + (lastPositions == null));

                manager.findLastVisibleItemPositions(lastPositions);
                int lastVisibleItemPosition = findMax(lastPositions);
                //Log.e("ListTest","the lastVisibleItemPosition is:" +lastVisibleItemPosition);
                int itemCount = manager.getItemCount();
                //Log.e("ListTest","the itemCount is:" +itemCount);
                //判断是否滑动到了最后一个item, 并且是向上滑动
                if (lastVisibleItemPosition == (itemCount - 1)) {
                    //Log.e("ListTest","the lastVisibleItemPosition == (itemCount - 1), will call onLoadMore()");
                    //加载更多
                    onLoadMore();
                } else {
                    //Log.e("ListTest","the lastVisibleItemPosition != (itemCount - 1)");
                }
            }
        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //获取最后一个完全显示的itemposition
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                //Log.e("ListTest","the lastVisibleItemPosition is:" +lastVisibleItemPosition);
                int itemCount = manager.getItemCount();
                //Log.e("ListTest","the itemCount is:" +itemCount);
                //判断是否滑动到了最后一个item, 并且是向上滑动
                if (lastVisibleItemPosition == (itemCount - 1)) {
                    //Log.e("ListTest","the lastVisibleItemPosition == (itemCount - 1), will call onLoadMore()");
                    //加载更多
                    onLoadMore();
                } else {
                    //Log.e("ListTest","the lastVisibleItemPosition != (itemCount - 1)");
                }
            }
        }

    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0;
    }

    /**
     * 加载更多回调
     */
    public abstract void onLoadMore();
}
