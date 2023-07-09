package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData

fun BoardResponse.toBoard(code: Int): Board {
    val mappedList = ArrayList<BoardData>()

    if (this.boardData != null) {
        for (board in this.boardData) {
            mappedList.add(
                BoardData(
                    uuid = board.uuid,
                    title = board.title,
                    num = board.num,
                    writer = board.writer,
                    writeDate = board.writeDate,
                )
            )
        }
    }

    return Board(
        lastPage = this.lastPage,
        statusCode = code,
        boardData = mappedList
    )
}
