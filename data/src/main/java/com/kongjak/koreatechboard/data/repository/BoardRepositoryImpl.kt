package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.mapper.BoardMapper
import com.kongjak.koreatechboard.data.repository.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class BoardRepositoryImpl(private val boardRemoteDataSource: BoardRemoteDataSource) :
    BoardRepository {
    override suspend fun getBoard(site: String, board: String, page: Int): Flow<ArrayList<Board>> {
        return flow {
            boardRemoteDataSource.getBoard(site, board, page)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(BoardMapper.mapToBoard(it))
                }
        }
    }
}