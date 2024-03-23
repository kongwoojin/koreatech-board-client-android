package com.kongjak.koreatechboard.ui.main.home

import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.ui.base.UiState

data class HomeBoardState(
    val isLoaded: Boolean = false,
    val isSuccess: Boolean = false,
    val boardList: Map<String, List<BoardData>?> = emptyMap(),
    val statusCode: Int = 0,
    val error: String = ""
) : UiState
