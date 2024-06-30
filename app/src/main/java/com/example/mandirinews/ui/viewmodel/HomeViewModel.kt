package com.example.mandirinews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mandirinews.data.repository.NewsRepository
import com.example.mandirinews.network.config.ApiResponse
import com.example.mandirinews.network.response.ArticlesItem
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {
    val newsFlow: Flow<PagingData<ArticlesItem>> = repository.getEverything().cachedIn(viewModelScope)
    val topHeadline: LiveData<ApiResponse<ArticlesItem?>> = liveData {
        emit(repository.getTopHeadline())
    }
    class HomeViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(newsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}