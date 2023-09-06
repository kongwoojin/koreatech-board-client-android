package com.kongjak.koreatechboard.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.usecase.SearchBoardWithTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchBoardWithTitleUseCase: SearchBoardWithTitleUseCase) :
    ViewModel() {

    fun getAPI(site: String, board: String, title: String): Flow<PagingData<BoardData>> =
        searchBoardWithTitleUseCase(site, board, title).cachedIn(viewModelScope)
}
