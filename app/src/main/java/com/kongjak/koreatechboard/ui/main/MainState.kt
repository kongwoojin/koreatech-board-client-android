package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.util.routes.Department

data class MainState(
    val defaultScreen: BottomNavigationItem = BottomNavigationItem.Home,
    val initDepartment: Int = 0,
    val userDepartment: Int = 0
)
