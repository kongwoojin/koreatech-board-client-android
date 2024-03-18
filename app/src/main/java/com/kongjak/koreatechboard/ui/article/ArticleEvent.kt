package com.kongjak.koreatechboard.ui.article

import com.kongjak.koreatechboard.ui.base.UiEvent
import java.util.UUID

sealed class ArticleEvent : UiEvent {
    data class FetchData(val department: String, val uuid: UUID) : ArticleEvent()
}
