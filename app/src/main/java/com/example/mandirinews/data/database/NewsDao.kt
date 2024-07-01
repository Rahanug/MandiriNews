package com.example.mandirinews.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mandirinews.data.model.News
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsEntity: News)

    @Query("SELECT * FROM news")
    fun getAllNews(): Flow<List<News>>

    @Query("DELETE FROM news WHERE url = :url")
    suspend fun deleteByUrl(url: String)

    @Query("SELECT EXISTS(SELECT 1 FROM news WHERE url = :url)")
    fun isArticleBookmarked(url: String): LiveData<Boolean>
}