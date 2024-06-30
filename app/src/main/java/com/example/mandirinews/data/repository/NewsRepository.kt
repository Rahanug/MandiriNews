package com.example.mandirinews.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mandirinews.data.paging.NewsPagingSource
import com.example.mandirinews.network.config.ApiResponse
import com.example.mandirinews.network.config.ApiService
import com.example.mandirinews.network.response.ArticlesItem
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val apiService: ApiService) {
    fun getEverything(): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService) }
        ).flow
    }

    suspend fun getTopHeadline(): ApiResponse<ArticlesItem?> {
        return try{
            val response = apiService.getTopHeadlines()
            val article = response.articles.firstOrNull()
            if (article != null) {
                ApiResponse.Success(article)
            } else {
                ApiResponse.Empty
            }
        }catch (e: Exception){
            ApiResponse.Error(e.message ?: "Unknown Error")
        }

    }
    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService)
            }.also { instance = it }

        private val TAG = "NewsRepository"
    }
}