package com.kongjak.koreatechboard.domain.repository

import androidx.paging.PagingData
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    fun getBoard(site: String, board: String): Flow<PagingData<BoardData>>
    suspend fun getBoardMinimum(site: String, board: String): ResponseResult<Board>
    fun searchTitle(site: String, board: String, title: String): Flow<PagingData<BoardData>>
}
