package com.kongjak.koreatechboard.ui.board

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.GetInitDepartmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardInitViewModel @Inject constructor(private val getInitDepartmentUseCase: GetInitDepartmentUseCase) :
    ViewModel() {

    private val _initDepartment = MutableLiveData(0)
    val initDepartment: LiveData<Int>
        get() = _initDepartment

    init {
        getInitDepartment()
    }

    private fun getInitDepartment() {
        viewModelScope.launch {
            getInitDepartmentUseCase().collectLatest {
                _initDepartment.value = it
            }
        }
    }
}
