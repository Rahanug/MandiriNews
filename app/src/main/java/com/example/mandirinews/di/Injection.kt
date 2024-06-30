package com.example.mandirinews.di

import com.example.mandirinews.data.repository.NewsRepository
import com.example.mandirinews.network.config.ApiConfig

object Injection {
    fun provideRepository(): NewsRepository {
        val apiService = ApiConfig.getApiService()
        return NewsRepository.getInstance(apiService)
    }
}