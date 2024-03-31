package com.kongjak.koreatechboard.ui.main.board

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.usecase.api.GetBoardUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardUseCase: GetBoardUseCase
) : BaseViewModel<BoardState, BoardEvent>(BoardState()) {

    fun getAPI(department: String, board: String): Boolean {
        if (uiState.value.department == department && uiState.value.board == board) {
            return true
        }
        sendEvent(BoardEvent.FetchData(department, board))
        return false
    }

    override fun reduce(oldState: BoardState, event: BoardEvent) {
        when (event) {
            is BoardEvent.FetchData -> {
                setState(
                    oldState.copy(
                        department = event.department,
                        board = event.board,
                        boardItemsMap = getBoardUseCase(event.department, event.board).cachedIn(
                            viewModelScope
                        )
                    )
                )
            }
        }
    }
}
