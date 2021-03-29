package com.dyamschikov.newsapiapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dyamschikov.newsapiapp.data.datasorce.NewsLocalDataSource
import com.dyamschikov.newsapiapp.data.datasorce.NewsRemoteDataSource
import com.dyamschikov.newsapiapp.data.datasorce.NewsRemotePagingDataSource
import com.dyamschikov.newsapiapp.data.local.ArticleEntity
import com.dyamschikov.newsapiapp.data.local.asUiModel
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.data.remote.model.Article
import com.dyamschikov.newsapiapp.data.remote.model.NetworkNewsContainer
import com.dyamschikov.newsapiapp.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NewsRepository {
    suspend fun fetchHeadlinesNews(country: String): Result<NetworkNewsContainer>
    suspend fun setHeadlinesToStorage(dataEntity: List<ArticleEntity>)
    suspend fun getHeadlineFromStorage(): List<ArticleUi>
    fun fetchEverythingNews(): Flow<PagingData<Article>>
}

class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsLocalDataSource,
    private val remotePagingDataSource: NewsRemotePagingDataSource
) : NewsRepository {
    override suspend fun fetchHeadlinesNews(country: String): Result<NetworkNewsContainer> {
        return remoteDataSource.fetchHeadlinesNews(country)
    }

    override suspend fun setHeadlinesToStorage(dataEntity: List<ArticleEntity>) {
        localDataSource.deleteAll()
        localDataSource.insertAll(dataEntity)
    }

    override suspend fun getHeadlineFromStorage(): List<ArticleUi> {
        return localDataSource.getHeadlinesEntity().asUiModel()
    }

    override fun fetchEverythingNews(): Flow<PagingData<Article>> {
        val pageConfig = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
            initialLoadSize = NETWORK_PAGE_SIZE
        )
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { remotePagingDataSource }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}