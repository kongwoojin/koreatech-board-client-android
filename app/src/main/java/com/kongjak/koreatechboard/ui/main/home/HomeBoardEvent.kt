package com.kongjak.koreatechboard.ui.main.home

import com.kongjak.koreatechboard.ui.base.UiEvent

sealed class HomeBoardEvent : UiEvent {
    data class FetchData(val department: String, val board: String) : HomeBoardEvent()
}
