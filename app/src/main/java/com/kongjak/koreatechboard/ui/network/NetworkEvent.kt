package com.kongjak.koreatechboard.ui.network

import com.kongjak.koreatechboard.ui.base.UiEvent

sealed class NetworkEvent : UiEvent {
    object Connected : NetworkEvent()
    object Disconnected : NetworkEvent()
}
