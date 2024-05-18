package com.kongjak.koreatechboard.domain.repository

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.model.Article

interface ArticleRepository {
    suspend fun getArticle(uuid: Uuid): APIResult<Article>
}
