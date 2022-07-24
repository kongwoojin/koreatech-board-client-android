package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.model.Board
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    suspend fun getBoard(site: String, board: String, page: Int): Flow<ArrayList<Board>>
}