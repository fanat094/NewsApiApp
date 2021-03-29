package com.dyamschikov.newsapiapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleEntity: List<ArticleEntity>)

    @Query("DELETE FROM article_table")
    suspend fun deleteArticle()

    @Query("SELECT * FROM article_table")
    suspend fun getHeadlinesEntity(): List<ArticleEntity>
}