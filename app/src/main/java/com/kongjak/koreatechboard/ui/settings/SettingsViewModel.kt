package com.kongjak.koreatechboard.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.GetDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDepartmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDepartmentUseCase: GetDepartmentUseCase,
    private val setDepartmentUseCase: SetDepartmentUseCase
) : ViewModel() {
    private val _department = MutableLiveData(0)
    val department: LiveData<Int>
        get() = _department

    init {
        getDepartment()
    }

    private fun getDepartment() {
        viewModelScope.launch {
            getDepartmentUseCase().collectLatest {
                _department.value = it
            }
        }
    }

    fun setDepartment(index: Int) {
        viewModelScope.launch {
            setDepartmentUseCase(index)
        }
    }
}
