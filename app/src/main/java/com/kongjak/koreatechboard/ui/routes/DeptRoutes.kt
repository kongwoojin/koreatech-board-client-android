package com.kongjak.koreatechboard.ui.routes

import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.util.enums.DeptEnums

sealed class Department(val stringResource: Int, val dept: DeptEnums) { // dept is route
    object School : Department(R.string.drawer_item_school, DeptEnums.SCHOOL)
    object Dorm : Department(R.string.drawer_item_dorm, DeptEnums.DORM)
    object Cse : Department(R.string.drawer_item_cse, DeptEnums.CSE)
    object Mechanical : Department(R.string.drawer_item_mechanical, DeptEnums.MECHANICAL)
    object Mechatronics : Department(R.string.drawer_item_mechatronics, DeptEnums.MECHATRONICS)
    object Ite : Department(R.string.drawer_item_ite, DeptEnums.ITE)
    object Ide : Department(R.string.drawer_item_ide, DeptEnums.IDE)
    object Arch : Department(R.string.drawer_item_arch, DeptEnums.ARCH)
    object Emc : Department(R.string.drawer_item_emc, DeptEnums.EMC)
    object Sim : Department(R.string.drawer_item_sim, DeptEnums.SIM)
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
