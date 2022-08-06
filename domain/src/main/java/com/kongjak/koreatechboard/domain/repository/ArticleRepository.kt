package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    suspend fun getArticle(site: String, url: String): Article
}