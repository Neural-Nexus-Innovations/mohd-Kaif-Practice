package com.example.newsapp.model

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("api/v1/search")
    suspend fun getNews(
        @Query("query") query: String,
        @Query("hitsPerPage") hitsPerPage: Int,
        @Query("page") page: Int
    ): HitsResponse

}