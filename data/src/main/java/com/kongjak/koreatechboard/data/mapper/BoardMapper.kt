package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData

object BoardMapper {
    fun mapToBoard(boardResponse: BoardResponse, code: Int): Board {
        val mappedList = ArrayList<BoardData>()

        if (boardResponse.boardData != null) {
            for (board in boardResponse.boardData) {
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
            lastPage = boardResponse.lastPage,
            statusCode = code,
            boardData = mappedList
        )
    }
}
