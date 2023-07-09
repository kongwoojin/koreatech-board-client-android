package com.kongjak.koreatechboard.domain.repository

import androidx.paging.PagingData
import com.kongjak.koreatechboard.domain.model.BoardData
import kotlinx.coroutines.flow.Flow

interface BoardRepository {
    fun getBoard(site: String, board: String): Flow<PagingData<BoardData>>
}
