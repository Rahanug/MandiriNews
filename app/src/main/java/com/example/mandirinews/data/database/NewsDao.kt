//package com.example.mandirinews.data.database
//
//import androidx.paging.PagingSource
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.mandirinews.data.model.News
//
//@Dao
//interface NewsDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(story: List<News>)
//
//    @Query("SELECT * FROM news")
//    fun getEverything(): PagingSource<Int, News>
//
//    @Query("DELETE FROM news")
//    suspend fun deleteAll()
//}