package com.kongjak.koreatechboard.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Board(
    val title: String?,
    @SerializedName("notice_type")
    val noticeType: String?,
    val num: String?,
    val writer: String?,
    @SerializedName("write_date")
    val writeDate: String?,
    val read: Int,
    @SerializedName("article_url")
    val articleUrl: String
) : Serializable