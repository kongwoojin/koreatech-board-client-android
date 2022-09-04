package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.mapper.BoardMapper
import com.kongjak.koreatechboard.data.repository.remote.BoardRemoteDataSource
import com.kongjak.koreatechboard.domain.model.Board
import com.kongjak.koreatechboard.domain.repository.ArticleRepository
import com.kongjak.koreatechboard.domain.repository.BoardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class BoardRepositoryImpl @Inject constructor(private val boardRemoteDataSource: BoardRemoteDataSource) :
    BoardRepository {
    override suspend fun getBoard(site: String, board: String, page: Int): Board {
        return BoardMapper.mapToBoard(boardRemoteDataSource.getBoard(site, board, page))
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BoardModule {

    @Binds
    abstract fun bindArticleRepository(
        boardRepositoryImpl: BoardRepositoryImpl
    ): BoardRepository
}
