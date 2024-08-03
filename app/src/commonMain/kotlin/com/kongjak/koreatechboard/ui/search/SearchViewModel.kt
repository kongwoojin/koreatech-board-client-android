package com.kongjak.koreatechboard.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.usecase.api.SearchBoardWithTitleUseCase
import com.kongjak.koreatechboard.util.ViewModelExt
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class SearchViewModel(private val searchBoardWithTitleUseCase: SearchBoardWithTitleUseCase) :
    ViewModelExt<SearchState, SearchSideEffect>(SearchState()) {

    fun getAPI(department: String, board: String, title: String) {
        intent {
            postSideEffect(SearchSideEffect.FetchData(department, board, title))
        }
    }

    fun handleSideEffect(sideEffect: SearchSideEffect) {
        when (sideEffect) {
            is SearchSideEffect.FetchData -> {
                intent {
                    reduce {
                        state.copy(
                            department = sideEffect.department,
                            board = sideEffect.board,
                            title = sideEffect.title,
                            boardData = searchBoardWithTitleUseCase(
                                sideEffect.department,
                                sideEffect.board,
                                sideEffect.title
                            ).cachedIn(
                                viewModelScope
                            )
                        )
                    }
                }
            }
        }
    }
}
