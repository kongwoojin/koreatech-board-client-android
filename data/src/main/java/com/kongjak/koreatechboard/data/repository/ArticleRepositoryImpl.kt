package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.datasource.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.mapper.ArticleMapper
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import java.util.UUID
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(private val articleRemoteDataSource: ArticleRemoteDataSource) :
    ArticleRepository {
    override suspend fun getArticle(uuid: UUID): ResponseResult<Article> {
        val response = articleRemoteDataSource.getArticle(uuid)
        return ArticleMapper.mapToArticle(response.body(), response.code())
    }
}
