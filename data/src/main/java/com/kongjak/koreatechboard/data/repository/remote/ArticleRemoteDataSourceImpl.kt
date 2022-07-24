package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.api.RetrofitBuilder
import com.kongjak.koreatechboard.data.model.ArticleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ArticleRemoteDataSourceImpl : ArticleRemoteDataSource {
    override fun getArticle(site: String, url: String): Flow<ArticleResponse> {
        return flow {
            emit(RetrofitBuilder.api.getArticle(site, url)[0])
        }
    }
}