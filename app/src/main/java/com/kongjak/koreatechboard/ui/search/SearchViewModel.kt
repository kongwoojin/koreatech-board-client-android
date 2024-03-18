package com.kongjak.koreatechboard.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.usecase.SearchBoardWithTitleUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchBoardWithTitleUseCase: SearchBoardWithTitleUseCase) :
    BaseViewModel<SearchState, SearchEvent>(SearchState()) {

    fun getAPI(department: String, board: String, title: String) {
        sendEvent(SearchEvent.FetchData(department, board, title))
    }

    override fun reduce(oldState: SearchState, event: SearchEvent) {
        when (event) {
            is SearchEvent.FetchData -> {
                setState(
                    oldState.copy(
                        department = event.department,
                        board = event.board,
                        title = event.title,
                        boardData = searchBoardWithTitleUseCase(
                            event.department,
                            event.board,
                            event.title
                        ).cachedIn(viewModelScope)
                    )
                )
            }
        }
    }
}
