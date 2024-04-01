package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.util.routes.Department

sealed class MainSideEffect {
    data class SetDefaultScreen(val defaultScreen: BottomNavigationItem) : MainSideEffect()
    data class SetDefaultDepartment(val defaultDepartment: Department) : MainSideEffect()
    data object SetOpenedFromNotification : MainSideEffect()
}
