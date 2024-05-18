package com.kongjak.koreatechboard.ui.notice

import com.benasher44.uuid.Uuid

sealed class NoticeSideEffect {
    data class GetAllNotices(val departments: List<String>) : NoticeSideEffect()
    data class UpdateRead(val uuid: Uuid, val read: Boolean) : NoticeSideEffect()
    data class DeleteNotice(val uuid: Uuid) : NoticeSideEffect()
    data class AddSelectedDepartment(val department: Int) : NoticeSideEffect()
    data class RemoveSelectedDepartment(val department: Int) : NoticeSideEffect()
}
