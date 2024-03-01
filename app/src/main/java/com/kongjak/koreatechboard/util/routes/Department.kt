package com.kongjak.koreatechboard.util.routes

import com.kongjak.koreatechboard.R

sealed class Department(val name: String, val stringResource: Int, val boards: List<BoardItem>) {
    object School : Department("school", R.string.department_school, schoolBoards)
    object Dorm : Department("dorm", R.string.department_dorm, dormBoards)
    object Cse : Department("cse", R.string.department_cse, cseBoards)
    object Mechanical : Department("mechanical", R.string.department_mechanical, mechanicalBoards)
    object Mechatronics : Department("mechatronics", R.string.department_mechatronics, mechatronicsBoards)
    object Ite : Department("ite", R.string.department_ite, iteBoards)
    object Ide : Department("ide", R.string.department_ide, ideBoards)
    object Arch : Department("arch", R.string.department_arch, archBoards)
    object Emc : Department("emc", R.string.department_emc, emcBoards)
    object Sim : Department("sim", R.string.department_sim, simBoards)
}
