package com.kongjak.koreatechboard.data.api

import io.ktor.client.statement.*
import java.util.UUID

interface API {
    suspend fun getBoard(
        site: String,
        board: String,
        page: Int = 1,
        numOfItems: Int = 20
    ): HttpResponse

    suspend fun getBoardMinimum(
        site: String,
        board: String,
        page: Int = 1,
        numOfItems: Int = 5
    ): HttpResponse

    suspend fun getArticle(
        uuid: UUID
    ): HttpResponse

    suspend fun searchBoardWithTitle(
        site: String,
        board: String,
        title: String,
        page: Int = 1
    ): HttpResponse
}
