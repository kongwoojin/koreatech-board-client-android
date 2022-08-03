package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.api.RetrofitBuilder
import com.kongjak.koreatechboard.data.model.BoardResponse

class BoardRemoteDataSourceImpl : BoardRemoteDataSource {
    override suspend fun getBoard(site: String, board: String, page: Int): ArrayList<BoardResponse> {
        return RetrofitBuilder.api.getBoard(site, board, page)
    }
}