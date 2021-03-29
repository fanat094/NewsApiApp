package com.dyamschikov.newsapiapp.di.module

import android.content.Context
import androidx.room.Room
import com.dyamschikov.newsapiapp.data.datasorce.NewsLocalDataSource
import com.dyamschikov.newsapiapp.data.local.AppDataBase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun provideNewsListLocalDataSource(
        database: AppDataBase
    ): NewsLocalDataSource {
        return NewsLocalDataSource(
            database.appDataBaseDao()
        )
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            APP_DATABASE
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO

    companion object {
        const val APP_DATABASE = "news_api_app_database"
    }
}