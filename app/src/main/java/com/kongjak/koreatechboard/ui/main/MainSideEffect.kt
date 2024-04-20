package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.util.routes.MainRoute

sealed class MainSideEffect {
    data class UpdateCurrentRoute(val currentRoute: MainRoute) : MainSideEffect()
    data object InitDepartmentUpdate : MainSideEffect()
    data object UserDepartmentUpdate : MainSideEffect()
}
