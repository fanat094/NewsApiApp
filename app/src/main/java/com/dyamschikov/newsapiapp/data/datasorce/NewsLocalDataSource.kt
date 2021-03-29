package com.dyamschikov.newsapiapp.data.datasorce

import com.dyamschikov.newsapiapp.data.local.AppDataBaseDao
import com.dyamschikov.newsapiapp.data.local.ArticleEntity

class NewsLocalDataSource internal constructor(
    private val appDataBaseDao: AppDataBaseDao
) {
    suspend fun insertAll(articleEntity: List<ArticleEntity>) {
        appDataBaseDao.insertArticle(articleEntity)
    }

    suspend fun deleteAll() {
        appDataBaseDao.deleteArticle()
    }

    suspend fun getHeadlinesEntity(): List<ArticleEntity> {
        return appDataBaseDao.getHeadlinesEntity()
    }
}