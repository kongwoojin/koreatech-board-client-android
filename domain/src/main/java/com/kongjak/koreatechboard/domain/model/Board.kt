package com.kongjak.koreatechboard.domain.model

import java.util.UUID

interface Board {
    val lastPage: Int
    val statusCode: Int
    val boardData: List<BoardData>?
}

interface BoardData {
    val uuid: UUID
    val title: String
    val num: String
    val writer: String
    val writeDate: String
    val read: Int
}
