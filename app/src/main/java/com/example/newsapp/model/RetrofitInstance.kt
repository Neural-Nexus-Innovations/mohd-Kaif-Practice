package com.example.newsapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import kotlin.jvm.java
import kotlin.getValue

object RetrofitInstance {

//    https://hn.algolia.com/api/v1/search?query=react

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://hn.algolia.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val modelClass by lazy {
        retrofit.create(NewsInterface::class.java)
    }
}