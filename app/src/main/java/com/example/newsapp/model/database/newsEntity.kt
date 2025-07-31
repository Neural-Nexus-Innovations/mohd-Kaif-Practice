package com.example.newsapp.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News")
data class newsEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val num_comments: Int,
    val url: String
)
