package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardUseCase @Inject constructor(private val boardRepository: BoardRepository) {
    suspend fun execute(site: String, board: String, page: Int): Board {
        return boardRepository.getBoard(site, board, page)
    }
}