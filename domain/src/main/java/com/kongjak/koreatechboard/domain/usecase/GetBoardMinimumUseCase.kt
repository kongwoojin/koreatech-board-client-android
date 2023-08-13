package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import javax.inject.Inject

class GetBoardMinimumUseCase @Inject constructor(private val boardRepository: BoardRepository) {
    suspend operator fun invoke(site: String, board: String): ResponseResult<Board> {
        return boardRepository.getBoardMinimum(site, board)
    }
}
