package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.datasource.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.data.mapper.BoardMapper
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(private val boardRemoteDataSource: BoardRemoteDataSource) :
    BoardRepository {
    override suspend fun getBoard(site: String, board: String, page: Int): Board {
        return BoardMapper.mapToBoard(boardRemoteDataSource.getBoard(site, board, page))
    }
}
