package com.kongjak.koreatechboard.ui.main

import androidx.lifecycle.ViewModel
import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ContainerHost<MainState, MainSideEffect>, ViewModel() {

    override val container = container<MainState, MainSideEffect>(MainState())

    fun handleSideEffect(sideEffect: MainSideEffect) {
        when (sideEffect) {
            is MainSideEffect.SetDefaultScreen -> intent {
                reduce {
                    state.copy(defaultScreen = sideEffect.defaultScreen)
                }
            }

            is MainSideEffect.SetDefaultDepartment -> intent {
                reduce {
                    state.copy(defaultDepartment = sideEffect.defaultDepartment)
                }
            }

            MainSideEffect.SetOpenedFromNotification -> intent {
                reduce {
                    state.copy(isOpenedFromNotification = true)
                }
            }
        }
    }

    fun setDefaultScreen(home: BottomNavigationItem) {
        intent {
            postSideEffect(MainSideEffect.SetDefaultScreen(home))
        }
    }

    fun setDefaultDepartment(defaultDepartment: Department) {
        intent {
            postSideEffect(MainSideEffect.SetDefaultDepartment(defaultDepartment))
        }
    }

    fun setOpenedFromNotification() {
        intent {
            postSideEffect(MainSideEffect.SetOpenedFromNotification)
        }
    }
}
