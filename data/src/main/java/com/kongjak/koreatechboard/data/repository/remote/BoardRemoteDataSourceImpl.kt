package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.api.RetrofitBuilder
import com.kongjak.koreatechboard.data.model.BoardResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BoardRemoteDataSourceImpl : BoardRemoteDataSource {
    override fun getBoard(site: String, board: String, page: Int): Flow<ArrayList<BoardResponse>> {
        return flow {
            emit(RetrofitBuilder.api.getBoard(site, board, page))
        }
    }
}