package com.example.newsapp.model

import com.example.newsapp.model.database.newsEntity


fun CardData.toEntity(): newsEntity {
    return newsEntity(title = this.title,
        author = this.author,
        num_comments = this.num_comments,
        url= this.url
    )
}
