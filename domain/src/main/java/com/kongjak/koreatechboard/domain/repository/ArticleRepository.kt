package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.model.Article

interface ArticleRepository {
    suspend fun getArticle(site: String, url: String): Article
}
