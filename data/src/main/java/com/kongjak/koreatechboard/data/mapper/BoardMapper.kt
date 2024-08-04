package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.base.ErrorType
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Board

fun BoardResponse?.mapToBoard(code: Int): ResponseResult<Board> {
    if (this == null) {
        return ResponseResult.Success(
            BoardResponse(
                lastPage = 1,
                statusCode = code,
                boardData = emptyList()
            )
        )
    }
    return if (code == 200) {
        ResponseResult.Success(
            BoardResponse(
                lastPage = this.lastPage,
                statusCode = code,
                boardData = this.boardData
            )
        )
    } else {
        ResponseResult.Error(ErrorType(code))
    }
}
