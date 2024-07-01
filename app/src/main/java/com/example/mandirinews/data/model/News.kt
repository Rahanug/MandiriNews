package com.example.mandirinews.data.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String,
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val sourceName: String? = null,
    val title: String? = null,
    val content: String? = null
)