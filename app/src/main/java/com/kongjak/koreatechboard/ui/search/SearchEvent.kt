package com.kongjak.koreatechboard.ui.search

import com.kongjak.koreatechboard.ui.base.UiEvent

sealed class SearchEvent : UiEvent {
    data class FetchData(val department: String, val board: String, val title: String) :
        SearchEvent()
}