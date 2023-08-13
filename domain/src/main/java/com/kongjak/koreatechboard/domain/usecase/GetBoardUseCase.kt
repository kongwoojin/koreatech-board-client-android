package com.kongjak.koreatechboard.domain.usecase

import androidx.paging.PagingData
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardUseCase @Inject constructor(private val boardRepository: BoardRepository) {
    fun execute(site: String, board: String): Flow<PagingData<BoardData>> {
        return boardRepository.getBoard(site, board)
    }
}
