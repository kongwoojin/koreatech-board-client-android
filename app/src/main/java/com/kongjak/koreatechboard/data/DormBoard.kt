package com.kongjak.koreatechboard.data

import java.io.Serializable

data class DormBoard(
    val title: String?,
    val num: String?,
    val writer: String?,
    val write_date: String?,
    val read: Int,
    val article_url: String
) : Serializable