package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _prevNetworkState = MutableLiveData<NetworkState>()
    val prevNetworkState: LiveData<NetworkState>
        get() = _prevNetworkState

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    init {
        getNetworkState()
    }

    private fun getNetworkState() {
        viewModelScope.launch {
            networkUtil.networkState().collectLatest {
                _prevNetworkState.value = _networkState.value
                _networkState.value = it
            }
        }
    }
}
