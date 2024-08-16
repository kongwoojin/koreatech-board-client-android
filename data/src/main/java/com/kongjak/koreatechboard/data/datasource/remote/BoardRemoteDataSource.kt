package com.kongjak.koreatechboard.data.datasource.remote

import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.model.BoardResponse
import retrofit2.Response
import javax.inject.Inject

class BoardRemoteDataSource @Inject constructor(private val api: API) {
    suspend fun getBoardMinimum(department: String, board: String): Response<BoardResponse> {
        return api.getBoardMinimum(department, board)
    }
}
