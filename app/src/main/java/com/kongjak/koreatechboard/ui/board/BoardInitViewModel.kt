package com.kongjak.koreatechboard.ui.board

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.GetInitDepartmentUseCase
import com.kongjak.koreatechboard.domain.usecase.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BoardInitViewModel @Inject constructor(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase,
    private val getInitDepartmentUseCase: GetInitDepartmentUseCase
) : BaseViewModel<BoardInitState, BoardInitEvent>(BoardInitState()) {

    init {
        getInitDepartment()
        getUserDepartment()
    }

    private fun getInitDepartment() {
        viewModelScope.launch {
            getInitDepartmentUseCase().collectLatest {
                sendEvent(BoardInitEvent.InitDepartmentUpdated(it))
            }
        }
    }

    private fun getUserDepartment() {
        viewModelScope.launch {
            getUserDepartmentUseCase().collectLatest {
                sendEvent(BoardInitEvent.UserDepartmentUpdated(it))
            }
        }
    }

    override fun reduce(oldState: BoardInitState, event: BoardInitEvent) {
        when (event) {
            is BoardInitEvent.InitDepartmentUpdated -> setState(oldState.copy(initDepartment = event.value))
            is BoardInitEvent.UserDepartmentUpdated -> setState(oldState.copy(userDepartment = event.value))
        }
    }
}
