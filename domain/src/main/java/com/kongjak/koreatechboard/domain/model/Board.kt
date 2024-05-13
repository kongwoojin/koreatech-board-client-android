package com.kongjak.koreatechboard.domain.model

import java.util.UUID

data class Board (
    val lastPage: Int,
    val statusCode: Int,
    val boardData: List<BoardData>?
)

interface BoardData {
    val uuid: UUID
    val title: String
    val num: Int
    val writer: String
    val writeDate: String
    val read: Int
    val isNew: Boolean
    val isNotice: Boolean
}
