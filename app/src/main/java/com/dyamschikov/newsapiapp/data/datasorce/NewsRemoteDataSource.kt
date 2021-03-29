package com.dyamschikov.newsapiapp.data.datasorce

import com.dyamschikov.newsapiapp.data.remote.apiservice.NewsService
import com.dyamschikov.newsapiapp.data.remote.model.NetworkNewsContainer
import com.dyamschikov.newsapiapp.utils.DataSource
import com.dyamschikov.newsapiapp.utils.Result
import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(private val newsService: NewsService) :
    DataSource() {

    suspend fun fetchHeadlinesNews(country: String): Result<NetworkNewsContainer> {
        return getResultDefault { newsService.fetchHeadlinesNews(country) }
    }
}
