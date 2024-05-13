package com.kongjak.koreatechboard.domain.usecase.api

import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import java.util.UUID
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(private val articleRepository: ArticleRepository) {
    suspend operator fun invoke(uuid: UUID): APIResult<Article> {
        return articleRepository.getArticle(uuid)
    }
}
