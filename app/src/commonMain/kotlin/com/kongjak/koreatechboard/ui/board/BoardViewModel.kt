package com.kongjak.koreatechboard.ui.board

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.usecase.api.GetBoardUseCase
import com.kongjak.koreatechboard.util.ViewModelExt
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class BoardViewModel(
    private val getBoardUseCase: GetBoardUseCase
) : ViewModelExt<BoardState, BoardEvent>(BoardState()) {

    fun getAPI(department: String, board: String) {
        intent {
            if (state.department == department && state.board == board) return@intent
            postSideEffect(BoardEvent.FetchData(department, board))
        }
    }

    fun handleSideEffect(sideEffect: BoardEvent) {
        when (sideEffect) {
            is BoardEvent.FetchData -> {
                intent {
                    reduce {
                        state.copy(
                            department = sideEffect.department,
                            board = sideEffect.board,
                            boardItem = getBoardUseCase(
                                sideEffect.department,
                                sideEffect.board
                            ).cachedIn(
                                viewModelScope
                            ),
                            isInitialized = true
                        )
                    }
                }
            }
        }
    }
}
