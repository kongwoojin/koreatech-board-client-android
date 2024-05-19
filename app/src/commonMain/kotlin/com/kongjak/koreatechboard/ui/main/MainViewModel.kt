package com.kongjak.koreatechboard.ui.main

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.DARK_THEME_DARK_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_LIGHT_THEME
import com.kongjak.koreatechboard.domain.DARK_THEME_SYSTEM_DEFAULT
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.GetDarkThemeUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.theme.GetDynamicThemeUseCase
import com.kongjak.koreatechboard.util.ViewModelExt
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class MainViewModel(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase,
    private val getInitDepartmentUseCase: GetInitDepartmentUseCase,
    private val getDynamicThemeUseCase: GetDynamicThemeUseCase,
    private val getDarkThemeUseCase: GetDarkThemeUseCase
) : ViewModelExt<MainState, MainSideEffect>(MainState()) {

    init {
        intent {
            postSideEffect(MainSideEffect.InitDepartmentUpdate)
            postSideEffect(MainSideEffect.UserDepartmentUpdate)
            postSideEffect(MainSideEffect.GetDynamicTheme)
            postSideEffect(MainSideEffect.GetDarkTheme)
        }
    }

    fun setExternalLink(url: String) {
        handleSideEffect(MainSideEffect.SetExternalLink(url))
    }

    fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.InitDepartmentUpdate -> viewModelScope.launch {
                getInitDepartmentUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(initDepartment = it)
                        }
                    }
                }
            }

            is MainSideEffect.UserDepartmentUpdate -> viewModelScope.launch {
                getUserDepartmentUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(userDepartment = it)
                        }
                    }
                }
            }

            MainSideEffect.GetDarkTheme -> viewModelScope.launch {
                getDynamicThemeUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(isDynamicTheme = it)
                        }
                    }
                }
            }

            MainSideEffect.GetDynamicTheme -> viewModelScope.launch {
                getDarkThemeUseCase().collectLatest {
                    intent {
                        when (it) {
                            DARK_THEME_SYSTEM_DEFAULT -> {
                                reduce {
                                    state.copy(isDarkTheme = null)
                                }
                            }

                            DARK_THEME_DARK_THEME -> {
                                reduce {
                                    state.copy(isDarkTheme = true)
                                }
                            }

                            DARK_THEME_LIGHT_THEME -> {
                                reduce {
                                    state.copy(isDarkTheme = false)
                                }
                            }
                        }
                    }
                }
            }

            is MainSideEffect.SetExternalLink -> {
                intent {
                    reduce {
                        state.copy(externalLink = sideEffect.url)
                    }
                }
            }
        }
    }
}
