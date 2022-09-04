package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.api.RetrofitBuilder
import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.data.repository.ArticleRepositoryImpl
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val api: API) : ArticleRemoteDataSource {
    override suspend fun getArticle(site: String, url: String): ArticleResponse {
        return api.getArticle(site, url)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ArticleRemoteDataSourceModule {

    @Binds
    abstract fun bindArticleRemoteDataSourceRepository(
        articleRemoteDataSourceImpl: ArticleRemoteDataSourceImpl
    ): ArticleRemoteDataSource
}
