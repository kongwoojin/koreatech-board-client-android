package com.kongjak.koreatechboard.data.dao

import com.kongjak.koreatechboard.data.enity.Article
import kotlinx.coroutines.flow.Flow
import java.util.*

interface ArticleDao {
    fun getAll(vararg departments: String): Flow<List<Article>>

    fun getArticle(uuid: UUID): Article

    fun insert(article: Article)

    fun insertAll(vararg articles: Article)

    fun updateRead(uuid: UUID, read: Boolean)

    fun delete(uuid: UUID)

    fun deleteAll()
}
