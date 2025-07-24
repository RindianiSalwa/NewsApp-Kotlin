package com.example.newsapp.model
data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt:String?,
    val source: Source?
)
data class Source(
    val name: String?
)