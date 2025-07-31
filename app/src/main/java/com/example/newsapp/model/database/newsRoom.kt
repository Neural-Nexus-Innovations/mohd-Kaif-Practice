package com.example.newsapp.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [newsEntity::class], version = 2, exportSchema = false)
abstract class newsRoom : RoomDatabase() {

    abstract fun newsDao(): newsDao

    companion object {
        @Volatile
        private var INSTANCE: newsRoom? = null

        fun getDB(context: Context): newsRoom {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    newsRoom::class.java,
                    "Task_db"
                ).fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
        }
    }
}
