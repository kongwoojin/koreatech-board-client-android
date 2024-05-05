package com.kongjak.koreatechboard.ui.settings

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.kongjak.koreatechboard.BuildConfig
import com.kongjak.koreatechboard.constraint.FCM_TOPIC_DORM
import com.kongjak.koreatechboard.constraint.FCM_TOPIC_SCHOOL
import com.kongjak.koreatechboard.domain.usecase.database.DeleteAllNewNoticesUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.SetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.SetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.GetDepartmentNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.GetDormNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.GetSchoolNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.SetDepartmentNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.SetDormNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.subscribe.SetSchoolNoticeSubscribe
import com.kongjak.koreatechboard.domain.usecase.settings.theme.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.SetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.SetDynamicThemeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
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
    private val setSchoolNoticeSubscribe: SetSchoolNoticeSubscribe,
    private val getSchoolNoticeSubscribe: GetSchoolNoticeSubscribe,
    private val setDormNoticeSubscribe: SetDormNoticeSubscribe,
    private val getDormNoticeSubscribe: GetDormNoticeSubscribe,
    private val setDepartmentNoticeSubscribe: SetDepartmentNoticeSubscribe,
    private val getDepartmentNoticeSubscribe: GetDepartmentNoticeSubscribe,
    private val deleteAllNewNoticesUseCase: DeleteAllNewNoticesUseCase
) : ContainerHost<SettingsState, SettingsSideEffect>, ViewModel() {

    override val container = container<SettingsState, SettingsSideEffect>(SettingsState())

    init {
        intent {
            postSideEffect(SettingsSideEffect.GetUserDepartment)
            postSideEffect(SettingsSideEffect.GetInitDepartment)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                postSideEffect(SettingsSideEffect.GetDynamicTheme)
            }
            postSideEffect(SettingsSideEffect.GetDarkTheme)
            postSideEffect(SettingsSideEffect.GetSchoolSubscribe)
            postSideEffect(SettingsSideEffect.GetDormSubscribe)
            postSideEffect(SettingsSideEffect.GetDepartmentSubscribe)
        }
    }

    fun sendSideEffect(sideEffect: SettingsSideEffect) {
        intent {
            postSideEffect(sideEffect)
        }
    }

    fun handleSideEffect(sideEffect: SettingsSideEffect) {
        when (sideEffect) {
            SettingsSideEffect.GetDarkTheme -> viewModelScope.launch {
                getDarkThemeUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(isDarkTheme = it)
                        }
                    }
                }
            }

            SettingsSideEffect.GetDynamicTheme -> viewModelScope.launch {
                getDynamicThemeUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(isDynamicTheme = it)
                        }
                    }
                }
            }

            SettingsSideEffect.GetInitDepartment -> viewModelScope.launch {
                getInitDepartmentUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(initDepartment = it)
                        }
                    }
                }
            }

            SettingsSideEffect.GetUserDepartment -> viewModelScope.launch {
                getUserDepartmentUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(userDepartment = it)
                        }
                    }
                }
            }

            is SettingsSideEffect.SetDarkTheme -> viewModelScope.launch {
                setDarkThemeUseCase(sideEffect.index)
                intent {
                    reduce {
                        state.copy(isDarkTheme = sideEffect.index)
                    }
                }
            }

            is SettingsSideEffect.SetDynamicTheme -> viewModelScope.launch {
                setDynamicThemeUseCase(sideEffect.state)
                intent {
                    reduce {
                        state.copy(isDynamicTheme = sideEffect.state)
                    }
                }
            }

            is SettingsSideEffect.SetInitDepartment -> viewModelScope.launch {
                setInitDepartmentUseCase(sideEffect.index)
                intent {
                    reduce {
                        state.copy(initDepartment = sideEffect.index)
                    }
                }
            }

            is SettingsSideEffect.SetUserDepartment -> viewModelScope.launch {
                setUserDepartmentUseCase(sideEffect.index)
                intent {
                    reduce {
                        state.copy(userDepartment = sideEffect.index)
                    }
                }
            }

            is SettingsSideEffect.UpdateSchoolSubscribe -> {
                viewModelScope.launch {
                    setSchoolNoticeSubscribe(sideEffect.subscribe)
                }
            }

            is SettingsSideEffect.UpdateDormSubscribe -> {
                viewModelScope.launch {
                    setDormNoticeSubscribe(sideEffect.subscribe)
                }
            }

            is SettingsSideEffect.UpdateDepartmentSubscribe -> {
                viewModelScope.launch {
                    setDepartmentNoticeSubscribe(sideEffect.subscribe)
                }
            }

            SettingsSideEffect.GetSchoolSubscribe -> viewModelScope.launch {
                getSchoolNoticeSubscribe().collectLatest {
                    intent {
                        reduce {
                            state.copy(subscribeSchool = it)
                        }
                        if (it) {
                            subscribeTopic(FCM_TOPIC_SCHOOL)
                        } else {
                            unsubscribeTopic(FCM_TOPIC_SCHOOL)
                        }
                    }
                }
            }

            SettingsSideEffect.GetDepartmentSubscribe -> viewModelScope.launch {
                getDepartmentNoticeSubscribe().collectLatest {
                    intent {
                        reduce {
                            state.copy(subscribeDepartment = it)
                        }
                        if (it) {
                            subscribeTopic(deptList[state.userDepartment].name)
                        } else {
                            unsubscribeTopic(deptList[state.userDepartment].name)
                        }
                    }
                }
            }

            SettingsSideEffect.GetDormSubscribe -> viewModelScope.launch {
                getDormNoticeSubscribe().collectLatest {
                    intent {
                        reduce {
                            state.copy(subscribeDormitory = it)
                        }
                        if (it) {
                            subscribeTopic(FCM_TOPIC_DORM)
                        } else {
                            unsubscribeTopic(FCM_TOPIC_DORM)
                        }
                    }
                }
            }

            SettingsSideEffect.DeleteAllNewArticle -> viewModelScope.launch(Dispatchers.IO) {
                deleteAllNewNoticesUseCase()
            }
        }
    }

    private fun subscribeTopic(topicRaw: String): Boolean {
        var isSuccess = true

        val topic = if (BuildConfig.BUILD_TYPE == "debug") {
            "development_$topicRaw"
        } else {
            topicRaw
        }

        Firebase.messaging.subscribeToTopic(topic)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    isSuccess = false
                }
            }

        return isSuccess
    }

    private fun unsubscribeTopic(topicRaw: String): Boolean {
        var isSuccess = true

        val topic = if (BuildConfig.BUILD_TYPE == "debug") {
            "development_$topicRaw"
        } else {
            topicRaw
        }

        Firebase.messaging.unsubscribeFromTopic(topic)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    isSuccess = false
                }
            }
        return isSuccess
    }
}
