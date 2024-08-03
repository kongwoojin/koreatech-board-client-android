package com.kongjak.koreatechboard.data.api

import com.benasher44.uuid.Uuid
import io.ktor.client.statement.HttpResponse

interface API {
    suspend fun getBoard(
        site: String,
        board: String,
        page: Int = 1,
        numOfItems: Int = 20
    ): HttpResponse

    suspend fun getBoardMinimum(
        site: String,
        board: String
    ): HttpResponse

    suspend fun getArticle(
        uuid: Uuid
    ): HttpResponse

    suspend fun searchBoardWithTitle(
        site: String,
        board: String,
        title: String,
        page: Int = 1
    ): HttpResponse
}
