package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.util.routes.MainRoute

data class MainState(
    val currentRoute: String = MainRoute.Home.name,
    val initDepartment: Int = 0,
    val userDepartment: Int = 0
)
