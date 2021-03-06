package com.seanutf.android.mvi.data.api

import com.seanutf.android.mvi.data.model.User
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<User>
}