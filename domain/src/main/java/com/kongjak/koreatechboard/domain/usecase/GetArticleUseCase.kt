package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow

class GetArticleUseCase(private val articleRepository: ArticleRepository) {
    suspend fun execute(site: String, url: String): Flow<Article> {
        return articleRepository.getArticle(site, url)
    }
}