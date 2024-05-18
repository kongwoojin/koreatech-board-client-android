package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.base.ErrorType
import com.kongjak.koreatechboard.domain.base.APIResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.model.Files

fun ArticleResponse.mapToArticle(statusCode: Int): APIResult<Article> {
    val mappedFileList = ArrayList<Files>()

    if (this.files.isNotEmpty()) {
        for (files in this.files) {
            mappedFileList.add(
                Files(
                    fileName = files.fileName,
                    fileUrl = files.fileUrl
                )
            )
        }
    }

    return if (statusCode == 200) {
        APIResult.Success(
            Article(
                statusCode = statusCode,
                title = this.title,
                writer = this.writer,
                content = this.content,
                date = this.date,
                articleUrl = this.articleUrl,
                files = mappedFileList
            )
        )
    } else {
        APIResult.Error(ErrorType(statusCode, this.error))
    }
}
