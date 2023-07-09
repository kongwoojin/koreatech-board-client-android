package com.kongjak.koreatechboard.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.paging.BoardPagingSource
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(private val api: API) :
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
}
