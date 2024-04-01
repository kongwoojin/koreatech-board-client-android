package com.kongjak.koreatechboard.domain.repository

import com.kongjak.koreatechboard.domain.model.LocalArticle
import java.util.UUID

interface DatabaseRepository {
    suspend fun getArticleList(): List<LocalArticle>
    suspend fun getArticle(uuid: UUID): LocalArticle
    suspend fun insertArticle(localArticle: LocalArticle)
    suspend fun insertArticleList(localArticleList: List<UUID>, department: String, board: String)
    suspend fun deleteArticle(uuid: UUID)
    suspend fun deleteAllArticle()
    suspend fun updateRead(uuid: UUID, read: Boolean)
}
