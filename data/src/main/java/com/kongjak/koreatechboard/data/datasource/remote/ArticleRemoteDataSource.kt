package com.kongjak.koreatechboard.data.datasource.remote

import com.kongjak.koreatechboard.data.api.API
import io.ktor.client.statement.*
import java.util.*

class ArticleRemoteDataSource(private val api: API) {
    suspend fun getArticle(uuid: UUID): HttpResponse {
        return api.getArticle(uuid)
    }
}
