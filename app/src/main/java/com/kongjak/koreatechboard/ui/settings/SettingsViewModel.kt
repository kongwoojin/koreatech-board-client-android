package com.kongjak.koreatechboard.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDynamicThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getDepartmentUseCase: GetDepartmentUseCase,
    private val setDepartmentUseCase: SetDepartmentUseCase,
    private val getDynamicThemeUseCase: GetDynamicThemeUseCase,
    private val setDynamicThemeUseCase: SetDynamicThemeUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase
) : ViewModel() {
    private val _department = MutableLiveData(0)
    val department: LiveData<Int>
        get() = _department

    private val _isDynamicTheme = MutableLiveData(true)
    val isDynamicTheme: LiveData<Boolean>
        get() = _isDynamicTheme

    private val _isDarkTheme = MutableLiveData(DARK_THEME_SYSTEM_DEFAULT)
    val isDarkTheme: LiveData<Int>
        get() = _isDarkTheme

    init {
        getDepartment()
        getDynamicTheme()
        getDarkTheme()
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

    private fun getDynamicTheme() {
        viewModelScope.launch {
            getDynamicThemeUseCase().collectLatest {
                _isDynamicTheme.value = it
            }
        }
    }

    fun setDynamicTheme(state: Boolean) {
        viewModelScope.launch {
            setDynamicThemeUseCase(state)
        }
    }

    private fun getDarkTheme() {
        viewModelScope.launch {
            getDarkThemeUseCase().collectLatest {
                _isDarkTheme.value = it
            }
        }
    }

    fun setDarkTheme(theme: Int) {
        viewModelScope.launch {
            setDarkThemeUseCase(theme)
        }
    }
}
