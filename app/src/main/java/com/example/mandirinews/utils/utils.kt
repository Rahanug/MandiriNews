package com.example.mandirinews.utils

import com.example.mandirinews.data.model.News
import com.example.mandirinews.network.response.ArticlesItem
import com.example.mandirinews.network.response.Source
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun getElapsedTime(publishedAt: String): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val publishedDate = formatter.parse(publishedAt)
    val currentDate = Date()

    val diffInMillis = currentDate.time - publishedDate.time
    val years = TimeUnit.MILLISECONDS.toDays(diffInMillis) / 365
    val months = (TimeUnit.MILLISECONDS.toDays(diffInMillis) % 365) / 30
    val days = TimeUnit.MILLISECONDS.toDays(diffInMillis) % 30
    val hours = TimeUnit.MILLISECONDS.toHours(diffInMillis) % 24
    val minutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis) % 60

    return when {
        years > 0 -> "$years years ago"
        months > 0 -> "$months months ago"
        days > 0 -> "$days days ago"
        hours > 0 -> "$hours hours ago"
        else -> "$minutes minutes ago"
    }
}

fun News.toArticlesItem(): ArticlesItem {
    return ArticlesItem(
        source = Source(name = this.sourceName),
        author = this.author,
        title = this.title,
        description = this.description,
        url = this.url,
        urlToImage = this.urlToImage,
        publishedAt = this.publishedAt,
        content = this.content
    )
}