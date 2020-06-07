package com.seanutf.android.mall.item.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.MergeAdapter
import com.seanutf.android.databinding.ActivityMallItemDetailBinding
import com.seanutf.android.mall.item.detail.adapter.MallItemDetailHeaderAdapter

class MallItemDetailActivity : AppCompatActivity() {
    lateinit var vb: ActivityMallItemDetailBinding
    lateinit var vm: MallItemDetailViewModel
    lateinit var adapter: MergeAdapter
    lateinit var headerAdapter: MallItemDetailHeaderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMallItemDetailBinding.inflate(this.layoutInflater)
        setContentView(vb.root)
        setViews()
        vm = ViewModelProvider.NewInstanceFactory().create(MallItemDetailViewModel::class.java)
    }

    private fun setViews() {
        headerAdapter = MallItemDetailHeaderAdapter(this)
        adapter = MergeAdapter()
        vb.rvList.adapter = adapter
    }
}