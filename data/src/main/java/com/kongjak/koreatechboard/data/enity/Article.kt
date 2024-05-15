package com.kongjak.koreatechboard.data.enity

import java.util.UUID

data class Article(
    val uuid: UUID,
    val num: Int,
    val title: String,
    val writer: String,
    val content: String,
    val date: String,
    val articleUrl: String,
    val department: String,
    val board: String,
    val read: Boolean,
    val isNotice: Boolean,
    val receivedTime: Long
)
