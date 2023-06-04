package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.model.Files

object ArticleMapper {
    fun mapToArticle(articleResponse: ArticleResponse, code: Int): Article {
        val mappedFileList = ArrayList<Files>()

        for (files in articleResponse.files) {
            mappedFileList.add(
                Files(
                    fileName = files.fileName,
                    fileUrl = files.fileUrl
                )
            )
        }

        return Article(
            statusCode = code,
            title = articleResponse.title,
            writer = articleResponse.writer,
            content = articleResponse.content,
            date = articleResponse.date,
            articleUrl = articleResponse.articleUrl,
            files = mappedFileList
        )
    }
}
