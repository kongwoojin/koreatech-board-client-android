package com.kongjak.koreatechboard.data.model

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("writer")
    val writer: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("files")
    val files: ArrayList<FilesResponse>
)
