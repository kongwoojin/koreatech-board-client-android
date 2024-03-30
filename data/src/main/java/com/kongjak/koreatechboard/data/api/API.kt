package com.kongjak.koreatechboard.data.api

import com.kongjak.koreatechboard.data.model.ArticleResponse
import com.kongjak.koreatechboard.data.model.BoardResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.UUID

interface API {
    @GET(APIConstraints.V3.BOARD)
    suspend fun getBoard(
        @Path("site") site: String,
        @Path("board") board: String,
        @Query("page") page: Int
    ): Response<BoardResponse>

    @GET(APIConstraints.V3.BOARD)
    suspend fun getBoardMinimum(
        @Path("site") site: String,
        @Path("board") board: String,
        @Query("page") page: Int = 1,
        @Query("num_of_items") numOfItems: Int = 5
    ): Response<BoardResponse>

    @GET(APIConstraints.V3.SEARCH_WITH_TITLE)
    suspend fun searchBoardWithTitle(
        @Path("site") site: String,
        @Path("board") board: String,
        @Query("title") title: String,
        @Query("page") page: Int = 1
    ): Response<BoardResponse>

    @GET(APIConstraints.V3.ARTICLE)
    suspend fun getArticle(
        @Query("uuid") uuid: UUID
    ): Response<ArticleResponse>
}
