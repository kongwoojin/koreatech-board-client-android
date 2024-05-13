package com.kongjak.koreatechboard.data.datasource.remote

import com.kongjak.koreatechboard.data.api.API
import io.ktor.client.statement.*
import javax.inject.Inject

class BoardRemoteDataSource @Inject constructor(private val api: API) {
    suspend fun getBoardMinimum(department: String, board: String): HttpResponse {
        return api.getBoardMinimum(department, board)
    }
}
