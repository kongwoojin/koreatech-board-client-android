package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData

object BoardMapper {
    fun mapToBoard(boardResponse: BoardResponse): Board {
        val mappedList = ArrayList<BoardData>()

        if (boardResponse.boardData != null) {
            for (board in boardResponse.boardData) {
                mappedList.add(
                    BoardData(
                        title = board.title,
                        noticeType = board.noticeType,
                        num = board.num,
                        writer = board.writer,
                        writeDate = board.writeDate,
                        read = board.read,
                        articleUrl = board.articleUrl
                    )
                )
            }
        }

        return Board(
            lastPage = boardResponse.lastPage,
            statusCode = boardResponse.statusCode,
            boardData = mappedList
        )
    }
}
