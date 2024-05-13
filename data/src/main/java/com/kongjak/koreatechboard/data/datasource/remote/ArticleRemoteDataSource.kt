package com.kongjak.koreatechboard.data.datasource.remote

import com.kongjak.koreatechboard.data.api.API
import io.ktor.client.statement.*
import java.util.*
import javax.inject.Inject

class ArticleRemoteDataSource @Inject constructor(private val api: API) {
    suspend fun getArticle(uuid: UUID): HttpResponse {
        return api.getArticle(uuid)
    }
}
