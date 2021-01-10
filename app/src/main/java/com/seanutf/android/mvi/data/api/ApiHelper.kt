package com.seanutf.android.mvi.data.api

import com.seanutf.android.mvi.data.model.User

interface ApiHelper {
    suspend fun getUsers(): List<User>
}