package com.example.mandirinews.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mandirinews.network.config.ApiService
import com.example.mandirinews.network.response.ArticlesItem

class NewsPagingSource(
    private val apiService: ApiService
) : PagingSource<Int, ArticlesItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticlesItem> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getEverything(apiKey = "b664802b1a9b4c7bb9d655ae4d586376", page = page, size = params.loadSize)
            val articles = response.articles ?: emptyList()
            LoadResult.Page(
                data = articles,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (articles.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ArticlesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}