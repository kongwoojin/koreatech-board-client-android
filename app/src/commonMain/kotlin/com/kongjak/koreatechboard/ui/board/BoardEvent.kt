package com.kongjak.koreatechboard.ui.board

sealed class BoardEvent {
    data class FetchData(val department: String, val board: String) : BoardEvent()
}
