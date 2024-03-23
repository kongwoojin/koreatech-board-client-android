package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.ui.base.BaseViewModel
import com.kongjak.koreatechboard.util.routes.Department

class MainViewModel : BaseViewModel<MainState, MainEvent>(MainState()) {
    override fun reduce(oldState: MainState, event: MainEvent) {
        when (event) {
            is MainEvent.SetDefaultScreen -> setState(oldState.copy(defaultScreen = event.defaultScreen))
            is MainEvent.SetDefaultDepartment -> setState(oldState.copy(defaultDepartment = event.defaultDepartment))
        }
    }

    fun setDefaultScreen(home: BottomNavigationItem) {
        sendEvent(MainEvent.SetDefaultScreen(home))
    }

    fun setDefaultDepartment(defaultDepartment: Department) {
        sendEvent(MainEvent.SetDefaultDepartment(defaultDepartment))
    }
}
