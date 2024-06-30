package com.example.mandirinews.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.mandirinews.network.config.ApiService
import com.example.mandirinews.network.response.ArticlesItem

@OptIn(ExperimentalPagingApi::class)
class NewsRemoteMediator(
    private val apiService: ApiService
) : RemoteMediator<Int, ArticlesItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticlesItem>
    ): MediatorResult {
        TODO("Not yet implemented")
    }


}