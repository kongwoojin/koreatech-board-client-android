package com.kongjak.koreatechboard.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.util.routes.MainRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase,
    private val getInitDepartmentUseCase: GetInitDepartmentUseCase
) : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())

    init {
        intent {
            postSideEffect(MainSideEffect.InitDepartmentUpdate)
            postSideEffect(MainSideEffect.UserDepartmentUpdate)
        }
    }

    fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.UpdateCurrentRoute -> intent {
                reduce {
                    state.copy(currentRoute = sideEffect.currentRoute.name)
                }
            }

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
        }
    }

    fun updateCurrentRoute(route: MainRoute) {
        intent {
            postSideEffect(MainSideEffect.UpdateCurrentRoute(route))
        }
    }
}
