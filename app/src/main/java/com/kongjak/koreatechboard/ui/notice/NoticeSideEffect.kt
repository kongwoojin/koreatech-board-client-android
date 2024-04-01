package com.kongjak.koreatechboard.ui.notice

import java.util.UUID

sealed class NoticeSideEffect {
    object GetAllNotices : NoticeSideEffect()
    data class UpdateRead(val uuid: UUID, val read: Boolean) : NoticeSideEffect()
}
