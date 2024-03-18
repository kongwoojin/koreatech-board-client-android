package com.kongjak.koreatechboard.ui.home

import com.kongjak.koreatechboard.ui.base.UiEvent
import com.kongjak.koreatechboard.util.routes.BoardItem

sealed class HomeBoardEvent : UiEvent {
    data class FetchData(val department: String, val board: String) : HomeBoardEvent()
}