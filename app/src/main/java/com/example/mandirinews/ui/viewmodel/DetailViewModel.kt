package com.example.mandirinews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mandirinews.data.model.News
import com.example.mandirinews.data.repository.NewsRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun saveArticle(article: News) {
        viewModelScope.launch {
            newsRepository.saveArticle(article)
        }
    }

    fun isArticleBookmarked(url: String): LiveData<Boolean> {
        return newsRepository.isArticleBookmarked(url)
    }

    fun deleteArticle(url: String) {
        viewModelScope.launch {
            newsRepository.deleteArticle(url)
        }
    }

    class DetailViewModelFactory(private val newsRepository: NewsRepository) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(newsRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}