package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.model.BoardResponse
import kotlinx.coroutines.flow.Flow

interface BoardRemoteDataSource {
    suspend fun getBoard(site: String, board: String, page: Int): ArrayList<BoardResponse>
}