package com.mildgrind.androidmvvm

data class NewsArticle(
    val articleId: String,
    val title: String,
    var sourceUrl: String,
    val description: String,
    val creator: String,
    val pubDate: String,
    val link: String,
)
