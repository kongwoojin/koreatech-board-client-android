package com.kongjak.koreatechboard.ui.home

sealed class HomeBoardEvent {
    data class FetchData(val department: String, val board: String) : HomeBoardEvent()
}
