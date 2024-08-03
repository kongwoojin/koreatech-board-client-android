package com.kongjak.koreatechboard.util.routes

import koreatech_board.app.generated.resources.Res
import koreatech_board.app.generated.resources.department_ace
import koreatech_board.app.generated.resources.department_arch
import koreatech_board.app.generated.resources.department_cse
import koreatech_board.app.generated.resources.department_dorm
import koreatech_board.app.generated.resources.department_ide
import koreatech_board.app.generated.resources.department_ite
import koreatech_board.app.generated.resources.department_mechanical
import koreatech_board.app.generated.resources.department_mechatronics
import koreatech_board.app.generated.resources.department_mse
import koreatech_board.app.generated.resources.department_school
import koreatech_board.app.generated.resources.department_sim
import org.jetbrains.compose.resources.StringResource

sealed class Department(val name: String, val stringResource: StringResource, val boards: List<BoardItem>) {
    object School : Department("school", Res.string.department_school, schoolBoards)
    object Dorm : Department("dorm", Res.string.department_dorm, dormBoards)
    object Cse : Department("cse", Res.string.department_cse, cseBoards)
    object Mechanical : Department("mechanical", Res.string.department_mechanical, mechanicalBoards)
    object Mechatronics : Department("mechatronics", Res.string.department_mechatronics, mechatronicsBoards)
    object Ite : Department("ite", Res.string.department_ite, iteBoards)
    object Ide : Department("ide", Res.string.department_ide, ideBoards)
    object Arch : Department("arch", Res.string.department_arch, archBoards)
    object Mse : Department("mse", Res.string.department_mse, mseBoards)
    object Ace : Department("ace", Res.string.department_ace, aceBoards)
    object Sim : Department("sim", Res.string.department_sim, simBoards)

    companion object {
        fun valueOf(name: String): Department {
            return when (name.lowercase()) {
                School.name -> School
                Dorm.name -> Dorm
                Cse.name -> Cse
                Mechanical.name -> Mechanical
                Mechatronics.name -> Mechatronics
                Ite.name -> Ite
                Ide.name -> Ide
                Arch.name -> Arch
                Mse.name -> Mse
                Ace.name -> Ace
                Sim.name -> Sim
                else -> School
            }
        }
    }
}
