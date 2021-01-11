package com.seanutf.android.mvi.ui.viewstate

import com.seanutf.android.mvi.data.model.User

sealed class MainState {

    object Idle : MainState()
    object Loading : MainState()
    data class Users(val user: List<User>) : MainState()
    data class Error(val error: String?) : MainState()
}
