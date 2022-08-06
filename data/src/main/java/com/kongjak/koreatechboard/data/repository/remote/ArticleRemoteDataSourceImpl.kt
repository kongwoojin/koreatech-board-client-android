package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.api.RetrofitBuilder
import com.kongjak.koreatechboard.data.model.ArticleResponse

class ArticleRemoteDataSourceImpl : ArticleRemoteDataSource {
    override suspend fun getArticle(site: String, url: String): ArticleResponse {
        return RetrofitBuilder.api.getArticle(site, url)[0]
    }
}