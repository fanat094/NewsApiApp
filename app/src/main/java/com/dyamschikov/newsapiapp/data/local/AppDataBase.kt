package com.dyamschikov.newsapiapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun appDataBaseDao(): AppDataBaseDao
}