package com.kongjak.koreatechboard.ui.main.board

sealed class BoardInitSideEffect {
    data class InitDepartmentUpdated(val value: Int) : BoardInitSideEffect()
    data class UserDepartmentUpdated(val value: Int) : BoardInitSideEffect()
}
