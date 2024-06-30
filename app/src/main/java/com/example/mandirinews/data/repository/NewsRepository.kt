package com.example.mandirinews.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mandirinews.data.paging.NewsPagingSource
import com.example.mandirinews.network.config.ApiService
import com.example.mandirinews.network.response.ArticlesItem
import kotlinx.coroutines.flow.Flow

class NewsRepository(private val apiService: ApiService) {
    fun getEverything(): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService) }
        ).flow
    }

    suspend fun getTopHeadline(): ArticlesItem? {
        val response = apiService.getTopHeadlines()
        return response.articles.firstOrNull()
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