package com.seanutf.android.mvi.data.repo

import com.seanutf.android.mvi.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getUsers() = apiHelper.getUsers()
}