package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.base.ErrorType
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Board

object BoardMapper {
    fun mapToBoard(boardResponse: BoardResponse?, code: Int): ResponseResult<Board> {
        return if (code == 200) {
            ResponseResult.Success(
                BoardResponse(
                    lastPage = boardResponse!!.lastPage,
                    statusCode = code,
                    boardData = boardResponse.boardData
                )
            )
        } else {
            ResponseResult.Error(ErrorType(code))
        }
    }
}
