package com.kongjak.koreatechboard.data.datasource.remote

import com.kongjak.koreatechboard.data.api.API
import io.ktor.client.statement.HttpResponse

class BoardRemoteDataSource(private val api: API) {
    suspend fun getBoardMinimum(department: String, board: String): HttpResponse {
        return api.getBoardMinimum(department, board)
    }
}
