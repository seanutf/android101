package com.seanutf.cmmonui.widget.viewgroup

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.seanutf.cmmonui.R
import kotlinx.android.synthetic.main.layout_footer.view.*


/**
 * Created by sean on 2019-04-22.
 * 加载更多
 */
class FooterView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var footerListener: FooterListener? = null
    //    var isLoadingMore = false
    private var currentState = STATE_LOADING
    var isAutoLoadMore = true //是否是自动加载更多 如果是的话 finish状态应该改为loading状态

    data class FooterTextStateModel(@FooterState val state: Int, val text: String, val textColor: Int, val backgroundColor: Int)

    interface FooterListener {
        fun onRetry()

        /**
         * 点击加载更多
         */
        fun onClickLoadMore() {
        }

    }

    fun setFooterListener(footerListener: FooterListener) {
        this.footerListener = footerListener
    }


    companion object {
        @IntDef(STATE_LOADING, STATE_FAIL, STATE_END, STATE_EMPTY, STATE_FINISH)
        @Retention(AnnotationRetention.SOURCE)
        annotation class FooterState

        const val STATE_LOADING = 0 //加载中
        const val STATE_FAIL = 1 //加载失败
        const val STATE_END = 2 //没有更多数据了
        const val STATE_EMPTY = 3 //空白页
        const val STATE_FINISH = 4 //加载完毕
    }


    init {
        init(context)
    }


    private fun init(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.layout_footer, this, true)
        /*layoutParams =
                LayoutParams(-1, -1)*/

        llFail.setOnClickListener {

            footerListener?.let {
                setState(STATE_LOADING)
                it.onRetry()
            }
        }

        llFinish.setOnClickListener {
            footerListener?.let {
                setState(STATE_LOADING)
                it.onClickLoadMore()
            }
        }


    }


    /**
     * 设置emptyview的高度
     */
    fun setEmptyViewHeight(height: Int) {
        var lp = llEmpty.layoutParams as? RelativeLayout.LayoutParams
        if (lp == null) {
            lp = RelativeLayout.LayoutParams(-1, height)
        } else {
            lp.height = height
        }
        llEmpty.layoutParams = lp
    }

    fun setEmptyView(view: View?) {
        if (view == null) return
        llEmpty.removeAllViews()
        llEmpty.addView(view)
    }

    /**
     * 设置不同状态下的文字颜色
     * @param textColorId 文字颜色 0是不改变默认颜色
     */
    fun setStateText(state: Int, text: String?, @ColorRes textColorId: Int, @ColorRes backgroundColor: Int) {
        val tv =
                when (state) {
                    STATE_LOADING -> tvLoading
                    STATE_FAIL -> tvFail
                    STATE_END -> tvEnd
                    STATE_EMPTY -> tvEmpty
                    STATE_FINISH -> tvFinish
                    else -> throw RuntimeException()
                }
        if (TextUtils.isEmpty(text).not()) {
            tv.text = text
        }
        if (textColorId != 0)
            tv.setTextColor(ContextCompat.getColor(context, textColorId))

        if (backgroundColor != 0) {
            val parentView =
                    when (state) {
                        STATE_LOADING -> rlLoading
                        STATE_FAIL -> llFail
                        STATE_END -> llEnd
                        STATE_EMPTY -> llEmpty
                        STATE_FINISH -> llFinish
                        else -> throw RuntimeException()
                    }
            parentView.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
        }

    }

    fun setState(@FooterState state: Int) {
        currentState = state
        when (state) {
            STATE_LOADING -> setLoadingState()
            STATE_FAIL -> setLoadFailedState()
            STATE_END -> setLoadEndState()
            STATE_EMPTY -> setEmptyState()
            STATE_FINISH -> if (isAutoLoadMore) setLoadingState() else setFinishState()
            else -> throw RuntimeException()
        }
    }

    private fun setFinishState() {
        //        isLoadingMore = false
        rlLoading.visibility = View.GONE
        llFail.visibility = View.GONE
        llEnd.visibility = View.GONE
        llEmpty.visibility = View.GONE
        llFinish.visibility = View.VISIBLE
    }

    private fun setLoadingState() {
        //        isLoadingMore = true
        rlLoading.visibility = View.VISIBLE
        llFail.visibility = View.GONE
        llEnd.visibility = View.GONE
        llEmpty.visibility = View.GONE
        llFinish.visibility = View.GONE

    }

    private fun setLoadFailedState() {
        //        isLoadingMore = false
        rlLoading.visibility = View.GONE
        llFail.visibility = View.VISIBLE
        llEnd.visibility = View.GONE
        llEmpty.visibility = View.GONE
        llFinish.visibility = View.GONE

    }

    private fun setLoadEndState() {
        //        isLoadingMore = false
        rlLoading.visibility = View.GONE
        llFail.visibility = View.GONE
        llEnd.visibility = View.VISIBLE
        llEmpty.visibility = View.GONE
        llFinish.visibility = View.GONE

    }

    private fun setEmptyState() {
        //        isLoadingMore = false
        rlLoading.visibility = View.GONE
        llFail.visibility = View.GONE
        llEnd.visibility = View.GONE
        llEmpty.visibility = View.VISIBLE
        llFinish.visibility = View.GONE

    }

}
