package com.kongjak.koreatechboard.data.model

import com.kongjak.koreatechboard.data.util.UUIDSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ArticleResponse(
    @SerialName("status_code")
    val statusCode: Int,
    @Serializable(with = UUIDSerializer::class)
    @SerialName("id")
    val uuid: UUID,
    @SerialName("num")
    val num: Int,
    @SerialName("title")
    val title: String,
    @SerialName("writer")
    val writer: String,
    @SerialName("content")
    val content: String,
    @SerialName("write_date")
    val date: String,
    @SerialName("article_url")
    val articleUrl: String,
    @SerialName("is_notice")
    val isNotice: Boolean,
    @SerialName("files")
    val files: ArrayList<FilesResponse>,
    @SerialName("error")
    val error: String
)
