package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.ui.state.NetworkState
import com.kongjak.koreatechboard.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkUtil: NetworkUtil
) : ViewModel() {
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    init {
        getNetworkState()
    }

    private fun getNetworkState() {
        viewModelScope.launch {
            networkUtil.networkState().collectLatest {
                _networkState.value = it
            }
        }
    }
}
