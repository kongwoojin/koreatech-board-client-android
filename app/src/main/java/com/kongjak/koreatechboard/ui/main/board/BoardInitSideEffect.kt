package com.kongjak.koreatechboard.ui.main.board

sealed class BoardInitSideEffect {
    object InitDepartmentUpdate : BoardInitSideEffect()
    object UserDepartmentUpdate : BoardInitSideEffect()
}
