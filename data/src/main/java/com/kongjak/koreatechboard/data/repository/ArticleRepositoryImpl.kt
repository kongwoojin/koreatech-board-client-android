package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.datasource.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.mapper.mapToArticle
import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import io.ktor.client.call.*
import java.util.*

class ArticleRepositoryImpl(private val articleRemoteDataSource: ArticleRemoteDataSource) :
    ArticleRepository {
    override suspend fun getArticle(uuid: UUID): APIResult<Article> {
        val response = articleRemoteDataSource.getArticle(uuid)
        val data: ArticleResponse = response.body()
        return data.mapToArticle(response.status.value)
    }
}
