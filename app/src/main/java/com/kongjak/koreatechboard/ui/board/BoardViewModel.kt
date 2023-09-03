package com.kongjak.koreatechboard.ui.board

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val getBoardUseCase: GetBoardUseCase) :
    ViewModel() {

    val tabIndex = mutableIntStateOf(0)

    fun getAPI(site: String, board: String): Flow<PagingData<BoardData>> =
        getBoardUseCase.execute(site, board).cachedIn(viewModelScope)

    fun changeIndex(index: Int) {
        tabIndex.intValue = index
    }
}
