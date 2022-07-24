package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.mapper.ArticleMapper
import com.kongjak.koreatechboard.data.repository.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class ArticleRepositoryImpl(private val articleRemoteDataSource: ArticleRemoteDataSource): ArticleRepository {
    override suspend fun getArticle(site: String, url: String): Flow<Article> {
        return flow {
            articleRemoteDataSource.getArticle(site, url)
                .retry(3)
                .catch {
                    print(it.toString())
                }
                .collect {
                    emit(ArticleMapper.mapToArticle(it))
                }
        }
    }
}