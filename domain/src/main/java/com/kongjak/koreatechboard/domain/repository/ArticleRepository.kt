package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Article
import java.util.UUID

interface ArticleRepository {
    suspend fun getArticle(uuid: UUID): ResponseResult<Article>
}
