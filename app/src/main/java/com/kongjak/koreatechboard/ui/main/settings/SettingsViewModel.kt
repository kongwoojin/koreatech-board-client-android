package com.kongjak.koreatechboard.ui.main.settings

import android.os.Build
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.GetShowArticleNumberUseCase
import com.kongjak.koreatechboard.domain.usecase.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetShowArticleNumberUseCase
import com.kongjak.koreatechboard.domain.usecase.SetUserDepartmentUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
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
) : BaseViewModel<SettingsState, SettingsEvent>(SettingsState()) {

    init {
        sendEvent(SettingsEvent.GetUserDepartment)
        sendEvent(SettingsEvent.GetInitDepartment)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            sendEvent(SettingsEvent.GetDynamicTheme)
        }
        sendEvent(SettingsEvent.GetDarkTheme)
        sendEvent(SettingsEvent.GetShowArticleNumber)
    }

    override fun reduce(oldState: SettingsState, event: SettingsEvent) {
        when (event) {
            SettingsEvent.GetDarkTheme -> viewModelScope.launch {
                getDarkThemeUseCase().collectLatest {
                    setState(oldState.copy(isDarkTheme = it))
                }
            }

            SettingsEvent.GetDynamicTheme -> viewModelScope.launch {
                getDynamicThemeUseCase().collectLatest {
                    setState(oldState.copy(isDynamicTheme = it))
                }
            }

            SettingsEvent.GetInitDepartment -> viewModelScope.launch {
                getInitDepartmentUseCase().collectLatest {
                    setState(oldState.copy(initDepartment = it))
                }
            }

            SettingsEvent.GetShowArticleNumber -> viewModelScope.launch {
                getShowArticleNumberUseCase().collectLatest {
                    setState(oldState.copy(showNumber = it))
                }
            }

            SettingsEvent.GetUserDepartment -> viewModelScope.launch {
                getUserDepartmentUseCase().collectLatest {
                    setState(oldState.copy(userDepartment = it))
                }
            }

            is SettingsEvent.SetDarkTheme -> viewModelScope.launch {
                setDarkThemeUseCase(event.index)
                sendEvent(SettingsEvent.GetDarkTheme)
            }

            is SettingsEvent.SetDynamicTheme -> viewModelScope.launch {
                setDynamicThemeUseCase(event.state)
                sendEvent(SettingsEvent.GetDynamicTheme)
            }

            is SettingsEvent.SetInitDepartment -> viewModelScope.launch {
                setInitDepartmentUseCase(event.index)
                sendEvent(SettingsEvent.GetInitDepartment)
            }

            is SettingsEvent.SetShowArticleNumber -> viewModelScope.launch {
                setShowArticleNumberUseCase(event.state)
                sendEvent(SettingsEvent.GetShowArticleNumber)
            }

            is SettingsEvent.SetUserDepartment -> viewModelScope.launch {
                setUserDepartmentUseCase(event.index)
                sendEvent(SettingsEvent.GetUserDepartment)
            }
        }
    }
}
