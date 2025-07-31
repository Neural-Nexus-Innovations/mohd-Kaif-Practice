// File: HitsResponse.kt
package com.example.newsapp.model

data class HitsResponse(
    val hits: List<CardData>
)

data class CardData(
    val title: String,
    val author: String,
    val num_comments: Int,
    val url: String

)
