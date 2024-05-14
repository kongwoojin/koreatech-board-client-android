package com.kongjak.koreatechboard.domain.usecase.api

import androidx.paging.PagingData
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow

class GetBoardUseCase(private val boardRepository: BoardRepository) {
    operator fun invoke(department: String, board: String): Flow<PagingData<BoardData>> {
        return boardRepository.getBoard(department, board)
    }
}
