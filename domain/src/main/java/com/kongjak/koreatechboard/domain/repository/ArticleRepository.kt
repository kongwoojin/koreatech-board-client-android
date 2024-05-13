package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.model.Article
import java.util.UUID

interface ArticleRepository {
    suspend fun getArticle(uuid: UUID): APIResult<Article>
}
