package com.kongjak.koreatechboard.data.repository

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.data.datasource.local.DatabaseLocalDataSource
import com.kongjak.koreatechboard.data.datasource.remote.ArticleRemoteDataSource
import com.kongjak.koreatechboard.data.mapper.mapToArticle
import com.kongjak.koreatechboard.data.mapper.mapToLocalArticle
import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.model.LocalArticle
import com.kongjak.koreatechboard.domain.repository.DatabaseRepository
import io.ktor.client.call.body
import kotlinx.coroutines.flow.flow

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

    override suspend fun getArticle(uuid: Uuid): LocalArticle {
        return databaseLocalDataSource.getArticle(uuid)
    }

    override suspend fun insertArticle(localArticle: LocalArticle) {
    }

    override suspend fun insertArticleList(
        localArticleList: List<Uuid>,
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

    override suspend fun deleteArticle(uuid: Uuid) {
        databaseLocalDataSource.deleteArticle(uuid)
    }

    override suspend fun deleteAllArticle() {
        databaseLocalDataSource.deleteAllArticle()
    }

    override suspend fun updateRead(uuid: Uuid, read: Boolean) {
        databaseLocalDataSource.updateRead(uuid, read)
    }
}
