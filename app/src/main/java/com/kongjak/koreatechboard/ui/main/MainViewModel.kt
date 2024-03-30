package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import com.kongjak.koreatechboard.util.routes.Department
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel<MainState, MainEvent>(MainState()) {
    override fun reduce(oldState: MainState, event: MainEvent) {
        when (event) {
            is MainEvent.SetDefaultScreen -> setState(oldState.copy(defaultScreen = event.defaultScreen))
            is MainEvent.SetDefaultDepartment -> setState(oldState.copy(defaultDepartment = event.defaultDepartment))
            MainEvent.SetOpenedFromNotification -> setState(oldState.copy(isOpenedFromNotification = true))
        }
    }

    fun setDefaultScreen(home: BottomNavigationItem) {
        sendEvent(MainEvent.SetDefaultScreen(home))
    }

    fun setDefaultDepartment(defaultDepartment: Department) {
        sendEvent(MainEvent.SetDefaultDepartment(defaultDepartment))
    }

    fun setOpenedFromNotification() {
        sendEvent(MainEvent.SetOpenedFromNotification)
    }
}
