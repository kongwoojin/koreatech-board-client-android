package com.kongjak.koreatechboard.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getDynamicThemeUseCase: GetDynamicThemeUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase
) : ViewModel() {
    private val _isDynamicTheme = MutableLiveData(true)
    val isDynamicTheme: LiveData<Boolean>
        get() = _isDynamicTheme

    private val _isDarkTheme = MutableLiveData<Boolean?>()
    val isDarkTheme: LiveData<Boolean?>
        get() = _isDarkTheme

    init {
        getDynamicTheme()
        getDarkTheme()
    }

    private fun getDynamicTheme() {
        viewModelScope.launch {
            getDynamicThemeUseCase().collectLatest {
                _isDynamicTheme.value = it
            }
        }
    }

    private fun getDarkTheme() {
        viewModelScope.launch {
            getDarkThemeUseCase().collectLatest {
                when (it) {
                    DARK_THEME_SYSTEM_DEFAULT -> {
                        _isDarkTheme.value = null
                    }
                    DARK_THEME_DARK_THEME -> {
                        _isDarkTheme.value = true
                    }
                    DARK_THEME_LIGHT_THEME -> {
                        _isDarkTheme.value = false
                    }
                }
            }
        }
    }
}
