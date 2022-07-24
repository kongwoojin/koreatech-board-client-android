package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.BoardResponse
import com.kongjak.koreatechboard.domain.model.Board

object BoardMapper {
    fun mapToBoard(list: ArrayList<BoardResponse>): ArrayList<Board> {
        val mappedList = ArrayList<Board>()

        for (board in list) {
            mappedList.add(
                Board(
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

        return mappedList
    }
}