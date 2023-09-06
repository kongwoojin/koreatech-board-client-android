package com.kongjak.koreatechboard.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.GetUserDepartmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase
) : ViewModel() {
    private val _department = MutableLiveData(0)
    val department: LiveData<Int>
        get() = _department

    init {
        getDepartment()
    }

    private fun getDepartment() {
        viewModelScope.launch {
            getUserDepartmentUseCase().collectLatest {
                _department.value = it
            }
        }
    }
}
