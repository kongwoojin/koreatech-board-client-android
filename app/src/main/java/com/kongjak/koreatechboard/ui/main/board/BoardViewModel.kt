package com.kongjak.koreatechboard.ui.main.board

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardUseCase: GetBoardUseCase
) : BaseViewModel<BoardState, BoardEvent>(BoardState()) {

    fun getAPI(department: String, board: String) {
        sendEvent(BoardEvent.FetchData(department, board))
    }

    override fun reduce(oldState: BoardState, event: BoardEvent) {
        when (event) {
            is BoardEvent.FetchData -> {
                if (oldState.department == event.department && oldState.board == event.board) {
                    return
                }
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
