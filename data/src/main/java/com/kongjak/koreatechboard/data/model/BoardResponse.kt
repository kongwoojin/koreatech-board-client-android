package com.kongjak.koreatechboard.data.model

import com.google.gson.annotations.SerializedName
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.model.BoardData
import java.util.UUID

data class BoardResponse(
    @SerializedName("last_page")
    override val lastPage: Int,
    @SerializedName("status_code")
    override val statusCode: Int,
    @SerializedName("posts")
    override val boardData: List<BoardResponseData>?
): Board

data class BoardResponseData(
    @SerializedName("id")
    override val uuid: UUID,
    @SerializedName("title")
    override val title: String,
    @SerializedName("num")
    override val num: String,
    @SerializedName("writer")
    override val writer: String,
    @SerializedName("write_date")
    override val writeDate: String,
    @SerializedName("read_count")
    override val read: Int,
): BoardData
