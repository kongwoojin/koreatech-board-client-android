package com.kongjak.koreatechboard.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.usecase.GetBoardMinimumUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDepartmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBoardMinimumUseCase: GetBoardMinimumUseCase,
    private val getDepartmentUseCase: GetDepartmentUseCase,
    ) : ViewModel() {
    private val _isLoaded = MutableLiveData(false)
    val isLoaded: LiveData<Boolean>
        get() = _isLoaded

    private val _boardList = mutableMapOf<String, MutableLiveData<List<BoardData>>>()
    val boardList: Map<String, LiveData<List<BoardData>>>
        get() = _boardList

    private val _statusCode = MutableLiveData(200)
    val statusCode: LiveData<Int>
        get() = _statusCode

    private val _department = MutableLiveData("")
    val department: LiveData<String>
        get() = _department

    init {
        _department.value = getDepartmentUseCase()
    }

    fun getApi(site: String, board: String) {
        val key = "$site:$board"

        if (!boardList.containsKey(key)) {
            _isLoaded.value = false
            _boardList[key] = MutableLiveData(emptyList())
            viewModelScope.launch {
                runCatching {
                    getBoardMinimumUseCase(site, board)
                }.onSuccess {
                    when (it) {
                        is ResponseResult.Success -> {
                            _boardList[key]!!.postValue(it.data.boardData)
                            _statusCode.value = it.data.statusCode
                            _isLoaded.postValue(true)
                        }
                        is ResponseResult.Error -> {
                            _statusCode.value = it.errorType.statusCode
                        }
                    }
                }.onFailure {
                }
            }
        }
    }
}
