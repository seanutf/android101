package com.seanutf.cmmonui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import com.seanutf.cmmonui.widget.recyclerView.EndlessSlidingListener
import com.seanutf.cmmonui.widget.viewgroup.FooterView

/**
 * Created by sean on 2019-05-06.
 * 带footerview的recyclerview的adapter
 * @param T item的数据类型
 */
abstract class CommonRecyclerViewAdapter<T, V : RecyclerView.ViewHolder>(val context: Context, val commonAdapterListener: CommonAdapterListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    protected lateinit var recyclerView: RecyclerView
    protected var footerView: FooterView? = null
    protected var footerEmptyView: View? = null
    protected var footerEmptyViewHeight: Int = 0
    protected var dataSet = ArrayList<T>()
    private var isLoadingMore: Boolean = false
    protected var isEnd: Boolean = false
    protected var needFooter: Boolean = true
    var isAutoLoadMore = true

    private val footerTextStateList = arrayOfNulls<FooterView.FooterTextStateModel>(5) //目前是5种state

    interface CommonAdapterListener {

        //自动加载更多
        fun onLoadMore()

        //点击重试
        fun onRetry()

        //点击加载更多
        fun onClickLoadMore() {
        }
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val footerView: FooterView = itemView as FooterView
    }


    final override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            val footerView = FooterView(context)
            footerView.isAutoLoadMore = isAutoLoadMore
            footerView.setEmptyView(footerEmptyView)
            if (footerEmptyViewHeight != 0)
                footerView.setEmptyViewHeight(footerEmptyViewHeight)
            this@CommonRecyclerViewAdapter.footerView = footerView
            footerTextStateList.forEach {
                it?.let { model ->
                    footerView.setStateText(model.state, model.text, model.textColor, model.backgroundColor)
                }
            }
            footerView.setFooterListener(object : FooterView.FooterListener {
                override fun onRetry() {
                    isLoadingMore = true
                    commonAdapterListener?.onRetry()
                }

                override fun onClickLoadMore() {

                    isLoadingMore = true
                    commonAdapterListener?.onClickLoadMore()
                }

            })
            FooterViewHolder(footerView)
        } else {
            return getViewHolder(viewGroup, viewType)
        }
    }

    /**
     * 返回你的viewholder
     */
    protected abstract fun getViewHolder(viewGroup: ViewGroup, viewType: Int): V

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FooterViewHolder) {
            bindFooterViewHolder(holder, position)
        } else
            bind(holder as V, position)

    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads == null || payloads.size == 0) {
            onBindViewHolder(holder, position)
        } else {
            bindWithPayloads(holder as V, position, payloads)
        }
    }

    final override fun getItemViewType(position: Int): Int {
        return if (position >= itemCount - 1 && needFooter) TYPE_FOOTER
        else getItemType(position)
    }

    /**
     * 返回当前view的类型
     */
    protected abstract fun getItemType(position: Int): Int

    /**
     * 判断是否是footer
     */
    protected fun isTypeFooterView(position: Int): Boolean {
        return getItemViewType(position) == TYPE_FOOTER
    }

    /**
     * 通过item数据查找列表中所在位置
     */
    protected fun getPositionByItem(data: T): Int {
        return dataSet.indexOf(data)
    }


    /**
     * 网络加载失败的调用
     */
    fun setNetworkFail() {
        isLoadingMore = false
        footerView?.setState(FooterView.STATE_FAIL)
    }


    private fun setCurrentFooterState(@FooterView.Companion.FooterState state: Int) {
        footerView?.setState(state)
    }

    /**
     * 暂时需要在setAdapter之前调用
     * */
    public fun isNeedFooterView(needFooter: Boolean) {
        this.needFooter = needFooter
    }

    /**
     * 一般在adapter初始化的时候调用此方法
     * 设置不同状态下的textview内容和颜色
     * @param textColor 文字颜色
     */
    fun setFooterStateText(@FooterView.Companion.FooterState state: Int, text: String, textColor: Int, backgroundColor: Int) {
        footerTextStateList[state] = FooterView.FooterTextStateModel(state, text, textColor, backgroundColor)
        footerView?.setStateText(state, text, textColor, backgroundColor)
    }

    fun setEmptyView(view: View) {
        this.footerEmptyView = view
        footerView?.setEmptyView(view)
    }

    fun setEmptyViewHeight(height: Int) {
        this.footerEmptyViewHeight = height
        footerView?.setEmptyViewHeight(height)
    }

    /**
     * @param isRefresh是否是刷新页面 是的话就清空现有的list
     * @param isEnd 是否是最后的数据了
     */
    fun setData(isRefresh: Boolean, isEnd: Boolean, dataSet: ArrayList<T>?) {

        this@CommonRecyclerViewAdapter.isEnd = isEnd
        isLoadingMore = false

        if (dataSet?.size == 0 && getDataSize() == 0) {
            setCurrentFooterState(FooterView.STATE_EMPTY)
        } else if (dataSet?.size == 0 || isEnd) {
            setCurrentFooterState(FooterView.STATE_END)
        } else {
            setCurrentFooterState(FooterView.STATE_FINISH)
        }

        if (isRefresh) {
            this.dataSet.clear()
        }
        dataSet?.let {

            //val oldData = dataSet
            this.dataSet.addAll(dataSet)
            //            if(recyclerView?.layoutManager is StaggeredGridLayoutManager){
            //                //由于StaggeredGridLayoutManager特性Item排列按空缺高度，有自动填空的机制
            //                //等等(spanIndex) 网上一些文章说最好不要用notifyDataSetChanged(), 而在项目中并无别
            //                //https://www.jianshu.com/p/d34075c0f287
            //                notifyItemRangeInserted(oldData.size, dataSet.size)
            //            } else {
            //                notifyDataSetChanged()
            //            }

            notifyDataSetChanged()
        }
    }

    fun clearData() {
        this.dataSet.clear()
        notifyDataSetChanged()
    }


    /**
     * 获取数据
     */
    fun getData(): List<T> = dataSet


    final override fun getItemCount() = count()

    private fun count(): Int {
        return if (needFooter) {
            dataSet.size + getChildExtCount() + 1
        } else {
            dataSet.size + getChildExtCount()
        }
    }

    /**
     * 子类可复写的，用于子类在数据之外额外添加条目数量
     * */
    open fun getChildExtCount(): Int {
        return 0
    }


    fun getDataSize(): Int = dataSet.size

    protected fun bindFooterViewHolder(holder: FooterViewHolder, position: Int) {

    }

    protected abstract fun bind(holder: V, position: Int)
    protected open fun bindWithPayloads(holder: V, position: Int, payloads: MutableList<Any>) {}

    /**
     * 一般情况下不要手动调用此方法，除非recyclerview的OnScrollListener无法触发，比如recyclerview嵌套在nestedscrollview中
     *  *当recyclerview滑动到底部时触发的OnScrollListener里的onLoadMore()里会自动调用此方法
     */
    fun onRecyclerViewLoadMore() {
        if (!isAutoLoadMore) {
            return
        }
        if (isLoadingMore || isEnd) {
        } else {
            isLoadingMore = true
            commonAdapterListener?.onLoadMore()
        }
    }

    @CallSuper
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        recyclerView.addOnScrollListener(object : EndlessSlidingListener() {
            override fun onLoadMore() {
                onRecyclerViewLoadMore()
            }

        })
    }

    companion object {
        const val TYPE_FOOTER = 123

    }
}