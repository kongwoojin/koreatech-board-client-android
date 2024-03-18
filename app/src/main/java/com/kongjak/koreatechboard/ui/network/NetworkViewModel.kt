package com.kongjak.koreatechboard.ui.network

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import com.kongjak.koreatechboard.util.NetworkUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val networkUtil: NetworkUtil
) : BaseViewModel<NetworkState, NetworkEvent>(NetworkState()) {

    init {
        getNetworkState()
    }

    private fun getNetworkState() {
        viewModelScope.launch {
            networkUtil.networkState().collectLatest { networkConnected ->
                if (networkConnected) {
                    sendEvent(NetworkEvent.Connected)
                } else {
                    sendEvent(NetworkEvent.Disconnected)
                }
            }
        }
    }

    override fun reduce(oldState: NetworkState, event: NetworkEvent) {
        when (event) {
            is NetworkEvent.Connected -> {
                setState(oldState.copy(isConnected = true))
            }

            is NetworkEvent.Disconnected -> {
                setState(oldState.copy(isConnected = false))
            }
        }
    }
}
