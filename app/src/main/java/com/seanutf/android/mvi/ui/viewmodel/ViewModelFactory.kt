package com.seanutf.android.mvi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seanutf.android.mvi.data.api.ApiHelper
import com.seanutf.android.mvi.data.repo.MainRepository

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}