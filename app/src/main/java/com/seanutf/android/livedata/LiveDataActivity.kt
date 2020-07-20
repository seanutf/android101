package com.seanutf.android.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.seanutf.android.databinding.ActivityLiveDataBinding

class LiveDataActivity : AppCompatActivity() {
    lateinit var vb: ActivityLiveDataBinding
    val viewModel: LiveDataViewModel by lazy {
        ViewModelProvider(this).get(LiveDataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(vb.root)

        vb.tvBtn.setOnClickListener {
            viewModel.startCount()
        }
    }
}
