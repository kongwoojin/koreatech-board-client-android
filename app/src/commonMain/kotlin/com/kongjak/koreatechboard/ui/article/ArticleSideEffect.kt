package com.kongjak.koreatechboard.ui.article

import com.benasher44.uuid.Uuid

sealed class ArticleSideEffect {
    data class FetchData(val department: String, val uuid: Uuid) : ArticleSideEffect()
}
