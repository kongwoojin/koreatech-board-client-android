package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.model.ArticleResponse
import kotlinx.coroutines.flow.Flow

interface ArticleRemoteDataSource {
    fun getArticle(site: String, url: String): Flow<ArticleResponse>
}