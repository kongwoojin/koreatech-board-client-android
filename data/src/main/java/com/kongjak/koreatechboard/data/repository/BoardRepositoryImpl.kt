package com.kongjak.koreatechboard.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.datasource.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.data.mapper.BoardMapper
import com.kongjak.koreatechboard.data.paging.BoardPagingSource
import com.kongjak.koreatechboard.data.paging.SearchTitlePagingSource
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(private val boardRemoteDataSource: BoardRemoteDataSource, private val api: API) :
    BoardRepository {
    override fun getBoard(site: String, board: String): Flow<PagingData<BoardData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BoardPagingSource(api, site, board) }
        ).flow
    }

    override suspend fun getBoardMinimum(site: String, board: String): ResponseResult<Board> {
        val response = boardRemoteDataSource.getBoardMinimum(site, board)
        return BoardMapper.mapToBoard(response.body(), response.code())
    }

    override fun searchTitle(
        site: String,
        board: String,
        title: String
    ): Flow<PagingData<BoardData>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchTitlePagingSource(api, site, board, title) }
        ).flow
    }
}
