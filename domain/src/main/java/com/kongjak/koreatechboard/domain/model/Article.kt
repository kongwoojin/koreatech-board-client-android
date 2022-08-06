package com.kongjak.koreatechboard.domain.model

data class Article(
    val title: String,
    val writer: String,
    val text: String,
    val date: String,
    val files: ArrayList<Files>)
