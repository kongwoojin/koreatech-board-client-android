package com.kongjak.koreatechboard.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.usecase.GetBoardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardViewModel @Inject constructor(private val getBoardUseCase: GetBoardUseCase) : ViewModel() {
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

    private lateinit var job: Job

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
        job = CoroutineScope(Dispatchers.IO).launch {
            _isLoading.postValue(true)
            _boardList.postValue(getBoardUseCase.execute(site.value!!, board.value!!, page.value!!))
            _isLoading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        _isLoading.value = false
        job.cancel()
    }
}
