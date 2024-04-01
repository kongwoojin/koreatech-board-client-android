package com.kongjak.koreatechboard.ui.main.board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
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
class BoardInitViewModel @Inject constructor(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase,
    private val getInitDepartmentUseCase: GetInitDepartmentUseCase
) : ContainerHost<BoardInitState, BoardInitSideEffect>, ViewModel() {

    override val container = container<BoardInitState, BoardInitSideEffect>(BoardInitState())

    init {
        intent {
            postSideEffect(BoardInitSideEffect.InitDepartmentUpdate)
            postSideEffect(BoardInitSideEffect.UserDepartmentUpdate)
        }
    }

    fun handleSideEffect(sideEffect: BoardInitSideEffect) {
        when (sideEffect) {
            is BoardInitSideEffect.InitDepartmentUpdate -> viewModelScope.launch {
                getInitDepartmentUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(initDepartment = it)
                        }
                    }
                }
            }

            is BoardInitSideEffect.UserDepartmentUpdate -> viewModelScope.launch {
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
}
