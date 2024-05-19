package com.kongjak.koreatechboard.data.datasource.remote

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.data.api.API
import io.ktor.client.statement.HttpResponse

class ArticleRemoteDataSource(private val api: API) {
    suspend fun getArticle(uuid: Uuid): HttpResponse {
        return api.getArticle(uuid)
    }
}
