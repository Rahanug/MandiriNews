package com.example.mandirinews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.mandirinews.data.model.News
import com.example.mandirinews.data.repository.NewsRepository

class FavoriteViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    val favoriteNews: LiveData<List<News>> = newsRepository.getFavoriteNews().asLiveData()

    class FavoriteViewModelFactory(private val newsRepository: NewsRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FavoriteViewModel(newsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}