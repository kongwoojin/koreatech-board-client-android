package com.kongjak.koreatechboard.domain.model

data class Article(
    val statusCode: Int,
    val title: String,
    val writer: String,
    val content: String,
    val date: String,
    val articleUrl: String,
    val files: ArrayList<Files>
)
