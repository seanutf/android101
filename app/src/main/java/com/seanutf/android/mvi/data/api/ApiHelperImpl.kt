package com.seanutf.android.mvi.data.api

import com.seanutf.android.mvi.data.model.User

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }
}