package com.kongjak.koreatechboard.ui.main.board

import com.kongjak.koreatechboard.ui.base.UiState

data class BoardInitState(
    val initDepartment: Int = 0,
    val userDepartment: Int = 0
) : UiState
