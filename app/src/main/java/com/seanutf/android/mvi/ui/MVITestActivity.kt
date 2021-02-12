package com.seanutf.android.mvi.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.seanutf.android.databinding.ActivityMviTestBinding
import com.seanutf.android.mvi.data.api.ApiHelperImpl
import com.seanutf.android.mvi.data.api.RetrofitBuilder
import com.seanutf.android.mvi.data.model.User
import com.seanutf.android.mvi.ui.action.MainAction
import com.seanutf.android.mvi.ui.adapter.MainAdapter
import com.seanutf.android.mvi.ui.viewmodel.MainViewModel
import com.seanutf.android.mvi.ui.viewmodel.ViewModelFactory
import com.seanutf.android.mvi.ui.viewstate.MainState
import com.seanutf.cmmonui.arch.BaseActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * MVI架构测试代码
 * https://blog.mindorks.com/mvi-architecture-android-tutorial-for-beginners-step-by-step-guide
 * */
class MVITestActivity : BaseActivity() {

    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())
    private lateinit var vb: ActivityMviTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = ActivityMviTestBinding.inflate(layoutInflater)
        setContentView(vb.root)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        vb.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userAction.send(MainAction.FetchUser)
            }
        }
    }


    private fun setupUI() {
        vb.recyclerView.layoutManager = LinearLayoutManager(this)
        vb.recyclerView.run {
            addItemDecoration(
                    DividerItemDecoration(
                            vb.recyclerView.context,
                            (vb.recyclerView.layoutManager as LinearLayoutManager).orientation
                    )
            )
        }
        vb.recyclerView.adapter = adapter
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(viewModelStore,
                ViewModelFactory(
                        ApiHelperImpl(
                                RetrofitBuilder.apiService))).get(MainViewModel::class.java)
    }


    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        vb.buttonFetchUser.visibility = View.GONE
                        vb.progressBar.visibility = View.VISIBLE
                    }

                    is MainState.Users -> {
                        vb.progressBar.visibility = View.GONE
                        vb.buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        vb.progressBar.visibility = View.GONE
                        vb.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MVITestActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        vb.recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}