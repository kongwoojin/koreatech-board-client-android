package com.kongjak.koreatechboard.data.api

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.data.model.BoardResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("{site}/{board}/")
    suspend fun getBoard(
        @Path("site") site: String,
        @Path("board") board: String,
        @Query("page") page: Int
    ): ArrayList<BoardResponse>

    @GET("{site}/article/")
    suspend fun getArticle(
        @Path("site") site: String,
        @Query("url") url: String
    ): ArrayList<ArticleResponse>
}
