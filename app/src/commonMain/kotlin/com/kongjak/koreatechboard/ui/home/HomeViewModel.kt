package com.kongjak.koreatechboard.ui.home

import androidx.lifecycle.viewModelScope
import com.kongjak.koreatechboard.domain.usecase.settings.department.GetUserDepartmentUseCase
import com.kongjak.koreatechboard.util.ViewModelExt
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce

class HomeViewModel(
    private val getUserDepartmentUseCase: GetUserDepartmentUseCase
) : ViewModelExt<HomeState, HomeSideEffect>(HomeState()) {

    init {
        getDepartment()
    }

    private fun getDepartment() {
        intent { postSideEffect(HomeSideEffect.GetDepartment) }
    }

    fun handleSideEffect(sideEffect: HomeSideEffect) {
        when (sideEffect) {
            HomeSideEffect.GetDepartment -> viewModelScope.launch {
                getUserDepartmentUseCase().collectLatest {
                    intent {
                        reduce {
                            state.copy(department = it)
                        }
                    }
                }
            }
        }
    }
}
