package com.dyamschikov.newsapiapp.data.remote.model

import com.dyamschikov.newsapiapp.data.local.ArticleEntity
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.data.modelui.SourceUi

data class NetworkNewsContainer(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

data class Source(
    val id: String?,
    val name: String?
)

fun NetworkNewsContainer.asDatabaseModel(): List<ArticleEntity> {
    return articles.map { articles ->
        ArticleEntity(
            author = articles.author,
            content = articles.content,
            description = articles.description,
            publishedAt = articles.publishedAt,
            title = articles.title,
            url = articles.url,
            urlToImage = articles.urlToImage,
            source_id = articles.source?.id,
            source_name = articles.source?.name,
            id = 0
        )
    }
}

fun Article.asDataUi(): ArticleUi {
    return ArticleUi(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        title = title,
        url = url,
        urlToImage = urlToImage,
        source = source.let { source ->
            SourceUi(
                id = source?.id,
                name = source?.name
            )
        }
    )
}