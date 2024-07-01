package com.example.mandirinews.di

import android.content.Context
import com.example.mandirinews.data.database.NewsDatabase
import com.example.mandirinews.data.repository.NewsRepository
import com.example.mandirinews.network.config.ApiConfig

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val newsDatabase = NewsDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return NewsRepository.getInstance(apiService, newsDatabase)
    }
}