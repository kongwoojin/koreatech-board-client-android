package com.kongjak.koreatechboard.domain.repository

import com.benasher44.uuid.Uuid
import com.kongjak.koreatechboard.domain.model.LocalArticle
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun getArticleList(vararg departments: String): Flow<List<LocalArticle>>
    suspend fun getArticle(uuid: Uuid): LocalArticle
    suspend fun insertArticle(localArticle: LocalArticle)
    suspend fun insertArticleList(localArticleList: List<Uuid>, department: String, board: String)
    suspend fun deleteArticle(uuid: Uuid)
    suspend fun deleteAllArticle()
    suspend fun updateRead(uuid: Uuid, read: Boolean)
}
