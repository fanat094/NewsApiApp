package com.dyamschikov.newsapiapp.data.datasorce

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dyamschikov.newsapiapp.data.remote.apiservice.NewsService
import com.dyamschikov.newsapiapp.data.remote.model.Article
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NewsRemotePagingDataSource @Inject constructor(private val newsService: NewsService) :
    PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: START_PAGE_INDEX
        return try {
            val response = newsService.fetchEverythingNews(
                "$DOMAIN_BBC, $DOMAIN_BBC_UA, $DOMAIN_24TV_UA, $DOMAIN_UNIAN_UA",
                page,
                PAGE_SIZE
            )
            val responseData = response.articles
            LoadResult.Page(
                data = responseData,
                prevKey = if (page == START_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val START_PAGE_INDEX = 1
        private const val PAGE_SIZE = 10
        private const val DOMAIN_BBC = "bbc.co.uk"
        private const val DOMAIN_BBC_UA = "bbc.com/ukrainian"
        private const val DOMAIN_24TV_UA = "24tv.ua"
        private const val DOMAIN_UNIAN_UA = "Unian.ua"
    }
}