package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.ui.base.UiEvent
import com.kongjak.koreatechboard.util.routes.Department

sealed class MainEvent : UiEvent {
    data class SetDefaultScreen(val defaultScreen: BottomNavigationItem) : MainEvent()
    data class SetDefaultDepartment(val defaultDepartment: Department) : MainEvent()
}
