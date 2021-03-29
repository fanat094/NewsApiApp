package com.dyamschikov.newsapiapp.data.remote.apiservice

import com.dyamschikov.newsapiapp.data.remote.model.NetworkNewsContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    companion object {
        const val ENDPOINT = "https://newsapi.org/v2/"
    }

    @GET("top-headlines/")
    suspend fun fetchHeadlinesNews(
        @Query("country") country: String
    ): Response<NetworkNewsContainer>

    @GET("everything/")
    suspend fun fetchEverythingNews(
        @Query("domains") domains: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): NetworkNewsContainer
}