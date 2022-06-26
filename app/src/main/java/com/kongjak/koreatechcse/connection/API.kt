package com.kongjak.koreatechcse.connection

import com.kongjak.koreatechcse.data.Article
import com.kongjak.koreatechcse.data.Board
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("cse/notice/")
    fun getNotice(
        @Query("page") page: Int
    ): Call<ArrayList<Board>>

    @GET("cse/free/")
    fun getFreeBoard(
        @Query("page") page: Int
    ): Call<ArrayList<Board>>

    @GET("cse/job/")
    fun getJobBoard(
        @Query("page") page: Int
    ): Call<ArrayList<Board>>

    @GET("cse/article/")
    fun getArticle(
        @Query("board") board: String,
        @Query("article") articleNum: Int
    ): Call<ArrayList<Article>>

}
