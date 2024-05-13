package com.kongjak.koreatechboard.data.api

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import java.util.UUID
import javax.inject.Inject

class APIImpl @Inject constructor(private val httpClient: HttpClient) : API {
    override suspend fun getBoard(site: String, board: String, page: Int, numOfItems: Int): HttpResponse {
        val response = httpClient.get {
            url("$site/$board")
            parameter("page", page)
            parameter("num_of_items", numOfItems)
        }

        return response.body()
    }

    override suspend fun getBoardMinimum(site: String, board: String, page: Int, numOfItems: Int): HttpResponse {
        val response = httpClient.get {
            url("$site/$board")
            parameter("page", page)
            parameter("num_of_items", numOfItems)
        }

        return response.body()    }

    override suspend fun getArticle(uuid: UUID): HttpResponse {
        val response = httpClient.get {
            url("article")
            parameter("uuid", uuid)
        }

        return response.body()
    }

    override suspend fun searchBoardWithTitle(site: String, board: String, title: String, page: Int): HttpResponse {
        val response = httpClient.get {
            url("$site/$board/search/title")
            parameter("title", title)
            parameter("page", page)
        }

        return response.body()
    }
}