package com.example.mandirinews.network.config

import com.example.mandirinews.network.response.NewsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String = "b664802b1a9b4c7bb9d655ae4d586376",
    ): NewsResponse

    @GET("everything?q=tesla&sortBy=publishedAt")
    suspend fun getEverything(
        @Query("apiKey") apiKey: String = "b664802b1a9b4c7bb9d655ae4d586376",
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): NewsResponse
}