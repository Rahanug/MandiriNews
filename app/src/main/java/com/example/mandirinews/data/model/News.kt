package com.example.mandirinews.data.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey
import com.example.mandirinews.network.response.ArticlesItem
import com.example.mandirinews.network.response.Source
import com.google.gson.annotations.SerializedName

//@Entity(tableName = "news")
data class News(
//    @PrimaryKey
    val id: String,
    val publishedAt: String? = null,
    val author: String? = null,
    val urlToImage: String? = null,
    val description: String? = null,
    val source: Source? = null,
    val title: String? = null,
    val url: String? = null,
    val content: String? = null
)