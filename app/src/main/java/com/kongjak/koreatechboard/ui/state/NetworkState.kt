package com.kongjak.koreatechboard.ui.state

sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
    object Unknown : NetworkState()
}
