package com.dyamschikov.newsapiapp.di.module

import com.dyamschikov.newsapiapp.BuildConfig
import com.dyamschikov.newsapiapp.data.remote.apiservice.NewsService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply { level = if (BuildConfig.DEBUG) BODY else NONE }

    @Provides
    fun provideApiKeyInterceptor(): Interceptor {
        return object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter(API_KEY_QUERY_PARAM, BuildConfig.NEWS_API_KEY)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                return chain.proceed(request)
            }
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideRemoteApiService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsService.ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    companion object {
        const val API_KEY_QUERY_PARAM = "apiKey"
    }
}