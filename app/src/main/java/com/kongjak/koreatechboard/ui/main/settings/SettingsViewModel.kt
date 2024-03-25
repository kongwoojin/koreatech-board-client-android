package com.kongjak.koreatechboard.ui.main.settings

import android.os.Build
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kongjak.koreatechboard.constraint.FCM_TOPIC_DORM
import com.kongjak.koreatechboard.constraint.FCM_TOPIC_SCHOOL
import com.kongjak.koreatechboard.domain.usecase.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetDepartmentNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.GetDormNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.GetSchoolNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.GetShowArticleNumberUseCase
import com.kongjak.koreatechboard.domain.usecase.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetDepartmentNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.SetDormNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.SetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.SetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.SetSchoolNoticeSubscribe
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
    private val setShowArticleNumberUseCase: SetShowArticleNumberUseCase,
    private val setSchoolNoticeSubscribe: SetSchoolNoticeSubscribe,
    private val getSchoolNoticeSubscribe: GetSchoolNoticeSubscribe,
    private val setDormNoticeSubscribe: SetDormNoticeSubscribe,
    private val getDormNoticeSubscribe: GetDormNoticeSubscribe,
    private val setDepartmentNoticeSubscribe: SetDepartmentNoticeSubscribe,
    private val getDepartmentNoticeSubscribe: GetDepartmentNoticeSubscribe
) : BaseViewModel<SettingsState, SettingsEvent>(SettingsState()) {

    init {
        sendEvent(SettingsEvent.GetUserDepartment)
        sendEvent(SettingsEvent.GetInitDepartment)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            sendEvent(SettingsEvent.GetDynamicTheme)
        }
        sendEvent(SettingsEvent.GetDarkTheme)
        sendEvent(SettingsEvent.GetShowArticleNumber)
        sendEvent(SettingsEvent.GetSchoolSubscribe)
        sendEvent(SettingsEvent.GetDormSubscribe)
        sendEvent(SettingsEvent.GetDepartmentSubscribe)
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

            is SettingsEvent.UpdateSchoolSubscribe -> viewModelScope.launch {
                setSchoolNoticeSubscribe(event.subscribe)
                sendEvent(SettingsEvent.GetSchoolSubscribe)
            }

            is SettingsEvent.UpdateDormSubscribe -> viewModelScope.launch {
                setDormNoticeSubscribe(event.subscribe)
                sendEvent(SettingsEvent.GetDormSubscribe)
            }

            is SettingsEvent.UpdateDepartmentSubscribe -> viewModelScope.launch {
                setDepartmentNoticeSubscribe(event.subscribe)
                sendEvent(SettingsEvent.GetDepartmentSubscribe)
            }

            SettingsEvent.GetSchoolSubscribe -> viewModelScope.launch {
                getSchoolNoticeSubscribe().collectLatest {
                    setState(oldState.copy(subscribeSchool = it))
                    if (it) {
                        subscribeTopic(FCM_TOPIC_SCHOOL)
                    } else {
                        unsubscribeTopic(FCM_TOPIC_SCHOOL)
                    }
                }
            }

            SettingsEvent.GetDepartmentSubscribe -> viewModelScope.launch {
                getDepartmentNoticeSubscribe().collectLatest {
                    setState(oldState.copy(subscribeDepartment = it))
                    if (it) {
                        subscribeTopic(deptList[uiState.value.userDepartment].name)
                    } else {
                        unsubscribeTopic(deptList[uiState.value.userDepartment].name)
                    }
                }
            }

            SettingsEvent.GetDormSubscribe -> viewModelScope.launch {
                getDormNoticeSubscribe().collectLatest {
                    setState(oldState.copy(subscribeDormitory = it))
                    if (it) {
                        subscribeTopic(FCM_TOPIC_DORM)
                    } else {
                        unsubscribeTopic(FCM_TOPIC_DORM)
                    }
                }
            }
        }
    }

    private fun subscribeTopic(topic: String): Boolean {
        var isSuccess = true
        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    isSuccess = false
                }
            }

        return isSuccess
    }

    private fun unsubscribeTopic(topic: String): Boolean {
        var isSuccess = true

        Firebase.messaging.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    isSuccess = false
                }
            }
        return isSuccess
    }
}
