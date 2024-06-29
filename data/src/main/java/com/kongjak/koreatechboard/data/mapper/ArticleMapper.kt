package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.base.ErrorType
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.model.Files

fun ArticleResponse?.mapToArticle(code: Int): ResponseResult<Article> {
    if (this == null) {
        return ResponseResult.Success(
            Article(
                statusCode = 404,
                title = "",
                writer = "",
                content = "",
                date = "",
                articleUrl = "",
                files = emptyList()
            )
        )
    }

    val mappedFileList = ArrayList<Files>()

    for (files in this.files) {
        mappedFileList.add(
            Files(
                fileName = files.fileName,
                fileUrl = files.fileUrl
            )
        )
    }

    return if (code == 200) {
        ResponseResult.Success(
            Article(
                statusCode = code,
                title = this.title,
                writer = this.writer,
                content = this.content,
                date = this.date,
                articleUrl = this.articleUrl,
                files = mappedFileList
            )
        )
    } else {
        ResponseResult.Error(ErrorType(code))
    }
}
