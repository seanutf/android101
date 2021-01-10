package com.seanutf.android.mvi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seanutf.android.mvi.data.repo.MainRepository
import com.seanutf.android.mvi.ui.action.MainAction
import com.seanutf.android.mvi.ui.viewstate.MainState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
        private val repository: MainRepository
) : ViewModel() {

    val userAction = Channel<MainAction>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state: StateFlow<MainState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userAction.consumeAsFlow().collect {
                when (it) {
                    is MainAction.FetchUser -> fetchUser()
                }
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            _state.value = try {
                MainState.Users(repository.getUsers())
            } catch (e: Exception) {
                MainState.Error(e.localizedMessage)
            }
        }
    }
}