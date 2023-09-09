package com.kongjak.koreatechboard.ui.settings

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.GetShowArticleNumberUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetShowArticleNumberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase,
    private val setUserDepartmentUseCase: SetUserDepartmentUseCase,
    private val getInitDepartmentUseCase: GetInitDepartmentUseCase,
    private val setInitDepartmentUseCase: SetInitDepartmentUseCase,
    private val getDynamicThemeUseCase: GetDynamicThemeUseCase,
    private val setDynamicThemeUseCase: SetDynamicThemeUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase,
    private val setDarkThemeUseCase: SetDarkThemeUseCase,
    private val getShowArticleNumberUseCase: GetShowArticleNumberUseCase,
    private val setShowArticleNumberUseCase: SetShowArticleNumberUseCase
) : ViewModel() {
    private val _userDepartment = MutableLiveData(0)
    val userDepartment: LiveData<Int>
        get() = _userDepartment

    private val _initDepartment = MutableLiveData(0)
    val initDepartment: LiveData<Int>
        get() = _initDepartment

    private val _isDynamicTheme = MutableLiveData(true)
    val isDynamicTheme: LiveData<Boolean>
        get() = _isDynamicTheme

    private val _isDarkTheme = MutableLiveData(DARK_THEME_SYSTEM_DEFAULT)
    val isDarkTheme: LiveData<Int>
        get() = _isDarkTheme

    private val _showNumber = MutableLiveData(true)
    val showNumber: LiveData<Boolean>
        get() = _showNumber

    init {
        getUserDepartment()
        getInitDepartment()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getDynamicTheme()
        }
        getDarkTheme()
        getShowArticleNumber()
    }

    private fun getUserDepartment() {
        viewModelScope.launch {
            getUserDepartmentUseCase().collectLatest {
                _userDepartment.value = it
            }
        }
    }

    fun setUserDepartment(index: Int) {
        viewModelScope.launch {
            setUserDepartmentUseCase(index)
        }
    }

    private fun getInitDepartment() {
        viewModelScope.launch {
            getInitDepartmentUseCase().collectLatest {
                _initDepartment.value = it
            }
        }
    }

    fun setInitDepartment(index: Int) {
        viewModelScope.launch {
            setInitDepartmentUseCase(index)
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

    private fun getShowArticleNumber() {
        viewModelScope.launch {
            getShowArticleNumberUseCase().collectLatest {
                _showNumber.value = it
            }
        }
    }

    fun setShowArticleNumber(state: Boolean) {
        viewModelScope.launch {
            setShowArticleNumberUseCase(state)
        }
    }
}
