package com.kongjak.koreatechboard.connection

import com.kongjak.koreatechboard.data.Article
import com.kongjak.koreatechboard.data.Board
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("{site}/{board}/")
    fun getBoard(
        @Path("site") site: String,
        @Path("board") board: String,
        @Query("page") page: Int
    ): Call<ArrayList<Board>>

    @GET("{site}/article/")
    fun getArticle(
        @Path("site") site: String,
        @Query("url") url: String
    ): Call<ArrayList<Article>>
}
