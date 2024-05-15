package com.kongjak.koreatechboard.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kongjak.koreatechboard.data.enity.Article
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ArticleDao {
    fun getAll(vararg departments: String): Flow<List<Article>>

    fun getArticle(uuid: UUID): Article

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg articles: Article)

    fun updateRead(uuid: UUID, read: Boolean)

    fun delete(uuid: UUID)

    fun deleteAll()
}
