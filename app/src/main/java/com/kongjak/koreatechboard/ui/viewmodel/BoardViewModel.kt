package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val getBoardUseCase: GetBoardUseCase) :
    ViewModel() {
    private val _boardList = MutableLiveData<List<BoardData>?>()
    val boardList: LiveData<List<BoardData>?>
        get() = _boardList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _page = MutableLiveData(1)
    val page: LiveData<Int>
        get() = _page

    private val _lastPage = MutableLiveData(1)
    val lastPage: LiveData<Int>
        get() = _lastPage

    private val _board = MutableLiveData<String>()
    val board: LiveData<String>
        get() = _board

    private val _site = MutableLiveData<String>()
    val site: LiveData<String>
        get() = _site

    private val _statusCode = MutableLiveData(200)
    val statusCode: LiveData<Int>
        get() = _statusCode

    init {
        _boardList.value = ArrayList()
    }

    fun nextPage() {
        if (_page.value!! < _lastPage.value!!) {
            _page.value = _page.value?.plus(1)
            getApi()
        }
    }

    fun prevPage() {
        if (_page.value!! > 1) {
            _page.value = _page.value?.minus(1)
            getApi()
        }
    }

    fun initData() {
        if (isLoading.value == false && boardList.value?.size == 0) {
            getApi()
        }
    }

    fun setBoardData(board: String) {
        _board.value = board
    }

    fun setSiteData(site: String) {
        _site.value = site
    }

    fun getApi() {
        viewModelScope.launch {
            _isLoading.value = true
            val data = getBoardUseCase.execute(site.value!!, board.value!!, page.value!!)
            _lastPage.value = data.lastPage
            _statusCode.value = data.statusCode
            _boardList.value = data.boardData
            _isLoading.value = false
        }
    }

    override fun onCleared() {
        super.onCleared()
        _isLoading.value = false
    }
}
