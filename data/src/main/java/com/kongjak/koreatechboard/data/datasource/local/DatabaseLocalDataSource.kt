package com.kongjak.koreatechboard.data.datasource.local

import com.kongjak.koreatechboard.data.dao.ArticleDao
import com.kongjak.koreatechboard.data.enity.Article
import com.kongjak.koreatechboard.data.mapper.mapToLocalArticle
import com.kongjak.koreatechboard.domain.model.LocalArticle
import java.util.UUID
import javax.inject.Inject

class DatabaseLocalDataSource @Inject constructor(
    private val articleDao: ArticleDao
) {
    fun getArticleList(): List<LocalArticle> {
        return articleDao.getAll().mapToLocalArticle()
    }

    fun getArticle(uuid: UUID): LocalArticle {
        return articleDao.getArticle(uuid).mapToLocalArticle()
    }

    fun insertArticle(article: Article) {
        articleDao.insert(article)
    }

    fun deleteArticle(uuid: UUID) {
        articleDao.delete(articleDao.getArticle(uuid))
    }

    fun deleteAllArticle() {
        articleDao.deleteAll(*articleDao.getAll().toTypedArray())
    }

    fun updateRead(uuid: UUID, read: Boolean) {
        articleDao.updateRead(uuid, read)
    }
}
