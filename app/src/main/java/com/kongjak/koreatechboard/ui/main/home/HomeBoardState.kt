package com.kongjak.koreatechboard.ui.main.home

import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.ui.base.UiState

data class HomeBoardState(
    val boardData: Map<String, HomeBoardData> = emptyMap(),
) : UiState {
    data class HomeBoardData(
        val isLoaded: Boolean = false,
        val isSuccess: Boolean = false,
        val boardData: List<BoardData> = emptyList(),
        val statusCode: Int = 0,
        val error: String = ""
    )
}
