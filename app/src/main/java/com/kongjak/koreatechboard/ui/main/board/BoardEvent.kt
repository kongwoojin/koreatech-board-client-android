package com.kongjak.koreatechboard.ui.main.board

import com.kongjak.koreatechboard.ui.base.UiEvent

sealed class BoardEvent : UiEvent {
    data class ShowNumberUpdated(val showNumber: Boolean) : BoardEvent()
    data class FetchData(val department: String, val board: String) : BoardEvent()
}
