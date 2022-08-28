package com.kongjak.koreatechboard.domain.usecase

import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend fun execute(site: String, url: String): Article {
        return articleRepository.getArticle(site, url)
    }
}