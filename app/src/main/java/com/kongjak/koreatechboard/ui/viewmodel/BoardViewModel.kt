package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BoardViewModel(private val getBoardUseCase: GetBoardUseCase) : ViewModel() {
    private val _boardList = MutableLiveData<ArrayList<Board>>()
    val boardList: LiveData<ArrayList<Board>>
        get() = _boardList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _page = MutableLiveData(1)
    val page: LiveData<Int>
        get() = _page

    private val _board = MutableLiveData<String>()
    val board: LiveData<String>
        get() = _board

    private val _site = MutableLiveData<String>()
    val site: LiveData<String>
        get() = _site

    init {
        _boardList.value = ArrayList()
    }

    fun nextPage() {
        _page.value = _page.value?.plus(1)
        getApi()
    }

    fun prevPage() {
        _page.value = _page.value?.minus(1)
        getApi()
    }

    fun initData() {
        if (boardList.value?.size == 0) {
            getApi()
        }
    }

    fun setBoardData(board: String) {
        _board.value = board
    }

    fun setSiteData(site: String) {
        _site.value = site
    }

    private fun getApi() {
        CoroutineScope(Dispatchers.IO).launch {
            _isLoading.postValue(true)
            getBoardUseCase.execute(site.value!!, board.value!!, page.value!!)
                .collect { boardData ->
                    _boardList.postValue(boardData)
                }
            _isLoading.postValue(false)
        }
    }
}
