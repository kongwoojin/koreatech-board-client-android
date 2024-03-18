package com.kongjak.koreatechboard.ui.board

import androidx.paging.PagingData
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.ui.base.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class BoardState(
    val department: String = "",
    val board: String = "",
    val showNumber: Boolean = true,
    val boardItemsMap: Flow<PagingData<BoardData>> = emptyFlow(),
) : UiState
