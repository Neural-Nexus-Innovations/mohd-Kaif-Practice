package com.example.newsapp.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface newsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: List<newsEntity>)

    @Query("SELECT * FROM News")
    suspend fun getAllNews(): List<newsEntity>
}
