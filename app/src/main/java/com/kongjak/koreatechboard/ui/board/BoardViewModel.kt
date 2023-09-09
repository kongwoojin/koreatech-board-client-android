package com.kongjak.koreatechboard.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import com.kongjak.koreatechboard.domain.usecase.GetShowArticleNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(
    private val getBoardUseCase: GetBoardUseCase,
    private val getShowArticleNumberUseCase: GetShowArticleNumberUseCase
    ) :
    ViewModel() {

    private val boardItemsMap = mutableMapOf<String, Flow<PagingData<BoardData>>?>()

    private val _showNumber = MutableLiveData(true)
    val showNumber: LiveData<Boolean>
        get() = _showNumber

    init {
        getShowArticleNumber()
    }

    fun getAPI(site: String, board: String): Flow<PagingData<BoardData>> {
        val key = "$site:$board"
        if (boardItemsMap[key] == null) {
            boardItemsMap[key] = getBoardUseCase(site, board).cachedIn(viewModelScope)
        }
        return boardItemsMap[key]!!
    }

    fun cleanUpCachedData(site: String ,board: String) {
        val key = "$site:$board"
        boardItemsMap[key] = null
    }

    private fun getShowArticleNumber() {
        viewModelScope.launch {
            getShowArticleNumberUseCase().collectLatest {
                _showNumber.value = it
            }
        }
    }
}
