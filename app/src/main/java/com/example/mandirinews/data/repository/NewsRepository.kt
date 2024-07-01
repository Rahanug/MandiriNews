package com.example.mandirinews.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mandirinews.data.database.NewsDatabase
import com.example.mandirinews.data.model.News
import com.example.mandirinews.data.paging.NewsPagingSource
import com.example.mandirinews.network.config.ApiResponse
import com.example.mandirinews.network.config.ApiService
import com.example.mandirinews.network.response.ArticlesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository(private val apiService: ApiService, private val newsDatabase: NewsDatabase) {
    fun getEverything(): Flow<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NewsPagingSource(apiService) }
        ).flow.flowOn(Dispatchers.IO)
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

    suspend fun saveArticle(newsEntity: News) {
        newsDatabase.newsDao().insert(newsEntity)
    }

    fun getFavoriteNews(): Flow<List<News>> {
        return newsDatabase.newsDao().getAllNews()
    }

    suspend fun deleteArticle(url: String) {
        newsDatabase.newsDao().deleteByUrl(url)
    }

    fun isArticleBookmarked(url: String): LiveData<Boolean> {
        return newsDatabase.newsDao().isArticleBookmarked(url)
    }
    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDatabase: NewsDatabase
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDatabase)
            }.also { instance = it }
    }
}