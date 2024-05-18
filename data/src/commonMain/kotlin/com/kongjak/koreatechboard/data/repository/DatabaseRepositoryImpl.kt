package com.kongjak.koreatechboard.data.repository

import com.kongjak.koreatechboard.data.datasource.local.DatabaseLocalDataSource
import com.kongjak.koreatechboard.data.datasource.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.mapper.mapToArticle
import com.kongjak.koreatechboard.data.mapper.mapToLocalArticle
import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.model.LocalArticle
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository
import io.ktor.client.call.*
import kotlinx.coroutines.flow.flow
import java.util.*

class DatabaseRepositoryImpl(
    private val databaseLocalDataSource: DatabaseLocalDataSource,
    private val articleRemoteDataSource: ArticleRemoteDataSource
) :
    DatabaseRepository {
    override suspend fun getArticleList(vararg departments: String) = flow {
        databaseLocalDataSource.getArticleList(*departments).collect {
            emit(it.mapToLocalArticle())
        }
    }

    override suspend fun getArticle(uuid: UUID): LocalArticle {
        return databaseLocalDataSource.getArticle(uuid)
    }

    override suspend fun insertArticle(localArticle: LocalArticle) {
    }

    override suspend fun insertArticleList(
        localArticleList: List<UUID>,
        department: String,
        board: String
    ) {
        localArticleList.map { uuid ->
            val response = articleRemoteDataSource.getArticle(uuid)
            val data: ArticleResponse = response.body()
            data.mapToArticle(department, board).let {
                databaseLocalDataSource.insertArticle(it)
            }
        }
    }

    override suspend fun deleteArticle(uuid: UUID) {
        databaseLocalDataSource.deleteArticle(uuid)
    }

    override suspend fun deleteAllArticle() {
        databaseLocalDataSource.deleteAllArticle()
    }

    override suspend fun updateRead(uuid: UUID, read: Boolean) {
        databaseLocalDataSource.updateRead(uuid, read)
    }
}
