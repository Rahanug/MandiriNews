package com.example.mandirinews.network.config

import com.example.mandirinews.network.response.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String = "f4eb3fcc3d9a4808a9f335bfc41dfeea",
    ): NewsResponse

    @GET("everything?q=tesla&sortBy=publishedAt")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String = "f4eb3fcc3d9a4808a9f335bfc41dfeea",
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): NewsResponse
}