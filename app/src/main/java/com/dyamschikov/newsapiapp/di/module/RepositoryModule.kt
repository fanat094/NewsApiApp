package com.dyamschikov.newsapiapp.di.module

import com.dyamschikov.newsapiapp.domain.repository.NewsRepository
import com.dyamschikov.newsapiapp.domain.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}