package com.kongjak.koreatechboard.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.repository.SettingsRepository
import com.kongjak.koreatechboard.domain.usecase.GetDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDepartmentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDepartmentUseCase: GetDepartmentUseCase,
    private val setDepartmentUseCase: SetDepartmentUseCase
) : ViewModel() {
    private val _department = MutableLiveData("")
    val department: LiveData<String>
        get() = _department

    init {
        getDepartment()
    }

    private fun getDepartment() {
        viewModelScope.launch {
            _department.value = getDepartmentUseCase()
        }
    }

    fun setDepartment(department: String) {
        _department.value = department
        viewModelScope.launch {
            setDepartmentUseCase(department)
        }
    }
}