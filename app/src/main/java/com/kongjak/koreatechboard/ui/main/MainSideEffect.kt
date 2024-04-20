package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem

sealed class MainSideEffect {
    data class SetDefaultScreen(val defaultScreen: BottomNavigationItem) : MainSideEffect()
    data object InitDepartmentUpdate : MainSideEffect()
    data object UserDepartmentUpdate : MainSideEffect()
}
