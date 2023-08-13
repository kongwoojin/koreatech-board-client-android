package com.kongjak.koreatechboard.util.routes

import com.kongjak.koreatechboard.R

sealed class Department(val name: String, val stringResource: Int, val boards: List<BoardItem>) {
    object School : Department("school", R.string.drawer_item_school, schoolBoards)
    object Dorm : Department("dorm", R.string.drawer_item_dorm, dormBoards)
    object Cse : Department("cse", R.string.drawer_item_cse, cseBoards)
    object Mechanical : Department("mechanical", R.string.drawer_item_mechanical, mechanicalBoards)
    object Mechatronics : Department("mechatronics", R.string.drawer_item_mechatronics, mechatronicsBoards)
    object Ite : Department("ite", R.string.drawer_item_ite, iteBoards)
    object Ide : Department("ide", R.string.drawer_item_ide, ideBoards)
    object Arch : Department("arch", R.string.drawer_item_arch, archBoards)
    object Emc : Department("emc", R.string.drawer_item_emc, emcBoards)
    object Sim : Department("sim", R.string.drawer_item_sim, simBoards)
}

val deptList = listOf(
    Department.School,
    Department.Dorm,
    Department.Cse,
    Department.Mechanical,
    Department.Mechatronics,
    Department.Ite,
    Department.Ide,
    Department.Arch,
    Department.Emc,
    Department.Sim
)