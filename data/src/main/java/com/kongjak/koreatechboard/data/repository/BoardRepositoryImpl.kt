package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.mapper.BoardMapper
import com.kongjak.koreatechboard.data.repository.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.repository.BoardRepository

class BoardRepositoryImpl(private val boardRemoteDataSource: BoardRemoteDataSource) :
    BoardRepository {
    override suspend fun getBoard(site: String, board: String, page: Int): ArrayList<Board> {
        return BoardMapper.mapToBoard(boardRemoteDataSource.getBoard(site, board, page))
    }
}