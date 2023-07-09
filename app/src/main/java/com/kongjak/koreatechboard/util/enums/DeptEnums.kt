package com.kongjak.koreatechboard.util.enums

import com.kongjak.koreatechboard.ui.routes.BoardItem
import com.kongjak.koreatechboard.ui.routes.archBoards
import com.kongjak.koreatechboard.ui.routes.cseBoards
import com.kongjak.koreatechboard.ui.routes.dormBoards
import com.kongjak.koreatechboard.ui.routes.emcBoards
import com.kongjak.koreatechboard.ui.routes.ideBoards
import com.kongjak.koreatechboard.ui.routes.iteBoards
import com.kongjak.koreatechboard.ui.routes.mechanicalBoards
import com.kongjak.koreatechboard.ui.routes.mechatronicsBoards
import com.kongjak.koreatechboard.ui.routes.schoolBoards
import com.kongjak.koreatechboard.ui.routes.simBoards

enum class DeptEnums(val navItem: List<BoardItem>) {
    SCHOOL(schoolBoards),
    DORM(dormBoards),
    CSE(cseBoards),
    MECHANICAL(mechanicalBoards),
    MECHATRONICS(mechatronicsBoards),
    ITE(iteBoards),
    IDE(ideBoards),
    ARCH(archBoards),
    EMC(emcBoards),
    SIM(simBoards)
}
