package com.kongjak.koreatechboard.domain.model

data class Board(
    val lastPage: Int,
    val statusCode: Int,
    val boardData: List<BoardData>?
)

data class BoardData(
    val title: String?,
    val noticeType: String?,
    val num: String?,
    val writer: String?,
    val writeDate: String?,
    val read: Int,
    val articleUrl: String
)
