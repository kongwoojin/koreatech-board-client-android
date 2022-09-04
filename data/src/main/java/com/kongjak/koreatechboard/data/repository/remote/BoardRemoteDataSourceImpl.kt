package com.kongjak.koreatechboard.data.repository.remote

import com.kongjak.koreatechboard.data.api.API
import com.kongjak.koreatechboard.data.api.RetrofitBuilder
import com.kongjak.koreatechboard.data.model.BoardResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class BoardRemoteDataSourceImpl @Inject constructor(private val api: API) : BoardRemoteDataSource {
    override suspend fun getBoard(site: String, board: String, page: Int): BoardResponse {
        return api.getBoard(site, board, page)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BoardRemoteDataSourceModule {

    @Binds
    abstract fun bindBoardRemoteDataSourceRepository(
        boardRemoteDataSourceImpl: BoardRemoteDataSourceImpl
    ): BoardRemoteDataSource
}
