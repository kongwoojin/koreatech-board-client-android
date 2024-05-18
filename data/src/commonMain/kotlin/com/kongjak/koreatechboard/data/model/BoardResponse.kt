package com.kongjak.koreatechboard.data.model

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.data.util.UUIDSerializer
import com.kongjak.koreatechboard.domain.model.BoardData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BoardResponse(
    @SerialName("last_page")
    val lastPage: Int,
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("posts")
    val boardData: List<BoardResponseData>?,
    @SerialName("error")
    val error: String
)

@Serializable
data class BoardResponseData(
    @Serializable(with = UUIDSerializer::class)
    @SerialName("id")
    override val uuid: Uuid,
    @SerialName("title")
    override val title: String,
    @SerialName("num")
    override val num: Int,
    @SerialName("writer")
    override val writer: String,
    @SerialName("write_date")
    override val writeDate: String,
    @SerialName("read_count")
    override val read: Int,
    @SerialName("is_new")
    override val isNew: Boolean,
    @SerialName("is_notice")
    override val isNotice: Boolean
) : BoardData
