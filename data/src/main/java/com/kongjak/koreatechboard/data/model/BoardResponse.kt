package com.kongjak.koreatechboard.data.model

import com.google.gson.annotations.SerializedName

data class BoardResponse(
    @SerializedName("title")
    val title: String?,
    @SerializedName("notice_type")
    val noticeType: String?,
    @SerializedName("num")
    val num: String?,
    @SerializedName("writer")
    val writer: String?,
    @SerializedName("write_date")
    val writeDate: String?,
    @SerializedName("read")
    val read: Int,
    @SerializedName("article_url")
    val articleUrl: String
)