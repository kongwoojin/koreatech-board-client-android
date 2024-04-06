package com.kongjak.koreatechboard.ui.main

import com.kongjak.koreatechboard.model.BottomNavigationItem
import com.kongjak.koreatechboard.util.routes.Department

data class MainState(
    val defaultScreen: BottomNavigationItem = BottomNavigationItem.Home,
    val defaultDepartment: Department? = null
)
