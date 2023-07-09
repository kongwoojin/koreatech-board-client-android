package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val getBoardUseCase: GetBoardUseCase) :
    ViewModel() {

    fun getAPI(site: String, board: String): Flow<PagingData<BoardData>> =
        getBoardUseCase.execute(site, board).cachedIn(viewModelScope)
}
