package com.kongjak.koreatechboard.ui.board

import com.kongjak.koreatechboard.ui.base.UiEvent

sealed class BoardInitEvent : UiEvent {
    data class InitDepartmentUpdated(val value: Int) : BoardInitEvent()
    data class UserDepartmentUpdated(val value: Int) : BoardInitEvent()
}
