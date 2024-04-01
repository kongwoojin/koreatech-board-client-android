package com.kongjak.koreatechboard.ui.main.board

sealed class BoardSideEffect {
    data class FetchData(val department: String, val board: String) : BoardSideEffect()
}
