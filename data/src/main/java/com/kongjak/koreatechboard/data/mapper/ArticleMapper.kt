package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.model.Files

object ArticleMapper {
    fun mapToArticle(articleResponse: ArticleResponse): Article {
        val mappedFileList = ArrayList<Files>()

        for (files in articleResponse.files) {
            mappedFileList.add(
                Files(
                    fileName = files.fileName,
                    fileUri = files.fileUri
                )
            )
        }

        return Article(
            statusCode = articleResponse.statusCode,
            title = articleResponse.title,
            writer = articleResponse.writer,
            text = articleResponse.text,
            date = articleResponse.date,
            files = mappedFileList
        )
    }
}
