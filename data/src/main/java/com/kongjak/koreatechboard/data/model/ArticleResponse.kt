package com.kongjak.koreatechboard.data.model

import com.google.gson.annotations.SerializedName
import java.util.UUID

data class ArticleResponse(
    @SerializedName("id")
    val uuid: UUID,
    @SerializedName("title")
    val title: String,
    @SerializedName("writer")
    val writer: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("write_date")
    val date: String,
    @SerializedName("article_url")
    val articleUrl: String,
    @SerializedName("files")
    val files: ArrayList<FilesResponse>
)
