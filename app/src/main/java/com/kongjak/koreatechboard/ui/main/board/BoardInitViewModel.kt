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
        getInitDepartment()
        getUserDepartment()
    }

    private fun getInitDepartment() {
        viewModelScope.launch {
            getInitDepartmentUseCase().collectLatest {
                intent {
                    postSideEffect(BoardInitSideEffect.InitDepartmentUpdated(it))
                }
            }
        }
    }

    private fun getUserDepartment() {
        viewModelScope.launch {
            getUserDepartmentUseCase().collectLatest {
                intent {
                    postSideEffect(BoardInitSideEffect.UserDepartmentUpdated(it))
                }
            }
        }
    }

    fun handleSideEffect(sideEffect: BoardInitSideEffect) {
        when (sideEffect) {
            is BoardInitSideEffect.InitDepartmentUpdated -> intent {
                reduce {
                    state.copy(initDepartment = sideEffect.value)
                }
            }

            is BoardInitSideEffect.UserDepartmentUpdated -> intent {
                reduce {
                    state.copy(userDepartment = sideEffect.value)
                }
            }
        }
    }
}
