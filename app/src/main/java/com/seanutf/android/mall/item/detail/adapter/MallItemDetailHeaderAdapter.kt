package com.seanutf.android.mall.item.detail.adapter

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seanutf.android.databinding.ItemDetailHeaderTopBinding
import com.seanutf.android.mall.item.detail.viewholder.ItemDetailHeaderImgViewHolder

class MallItemDetailHeaderAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_VIEW_HEADER_IMG = 1
        const val TYPE_VIEW_HEADER_SOURCE = 2
        const val TYPE_VIEW_HEADER_BRAND = 3
    }

    var context: Context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_VIEW_HEADER_IMG -> {
                var headerView = ItemDetailHeaderTopBinding.inflate((context as Activity).layoutInflater, parent, false)
                return ItemDetailHeaderImgViewHolder(headerView)
            }

            TYPE_VIEW_HEADER_SOURCE -> {
                var headerView = ItemDetailHeaderTopBinding.inflate((context as Activity).layoutInflater, parent, false)
                return ItemDetailHeaderImgViewHolder(headerView)
            }

            TYPE_VIEW_HEADER_BRAND -> {
                var headerView = ItemDetailHeaderTopBinding.inflate((context as Activity).layoutInflater, parent, false)
                return ItemDetailHeaderImgViewHolder(headerView)
            }

            else -> {
                var headerView = ItemDetailHeaderTopBinding.inflate((context as Activity).layoutInflater, parent, false)
                return ItemDetailHeaderImgViewHolder(headerView)
            }
        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return TYPE_VIEW_HEADER_IMG
            }

            1 -> {
                return TYPE_VIEW_HEADER_SOURCE
            }

            else -> {
                return TYPE_VIEW_HEADER_BRAND
            }
        }
    }
}