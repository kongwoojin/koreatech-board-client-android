package com.kongjak.koreatechboard.data.mapper

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.domain.base.ErrorType
import com.kongjak.koreatechboard.domain.base.ResponseResult
import com.kongjak.koreatechboard.domain.model.Article
import com.kongjak.koreatechboard.domain.model.Files

object ArticleMapper {
    fun mapToArticle(articleResponse: ArticleResponse?, code: Int): ResponseResult<Article> {
        val mappedFileList = ArrayList<Files>()

        if (articleResponse?.files != null) {
            for (files in articleResponse.files) {
                mappedFileList.add(
                    Files(
                        fileName = files.fileName,
                        fileUrl = files.fileUrl
                    )
                )
            }
        }

        return if (code == 200) {
            ResponseResult.Success(
                Article(
                    statusCode = code,
                    title = articleResponse!!.title,
                    writer = articleResponse.writer,
                    content = articleResponse.content,
                    date = articleResponse.date,
                    articleUrl = articleResponse.articleUrl,
                    files = mappedFileList
                )
            )
        } else {
            ResponseResult.Error(ErrorType(code))
        }
    }
}
