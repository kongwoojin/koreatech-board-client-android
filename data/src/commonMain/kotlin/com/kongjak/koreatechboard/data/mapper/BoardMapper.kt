package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.base.ErrorType
import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.model.Board

fun BoardResponse.mapToBoard(): APIResult<Board> {
    return if (this.statusCode == 200) {
        APIResult.Success(
            Board(
                lastPage = this.lastPage,
                statusCode = this.statusCode,
                boardData = this.boardData ?: emptyList()
            )
        )
    } else {
        APIResult.Error(ErrorType(this.statusCode, this.error))
    }
}
