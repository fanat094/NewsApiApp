package com.dyamschikov.newsapiapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.data.modelui.SourceUi

@Entity(tableName = "article_table")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String?,
    @ColumnInfo(name = "source_id")
    val source_id: String?,
    @ColumnInfo(name = "source_name")
    val source_name: String?
)

fun List<ArticleEntity>.asUiModel(): List<ArticleUi> {
    return map { entity ->
        ArticleUi(
            author = entity.author,
            content = entity.content,
            description = entity.description,
            publishedAt = entity.publishedAt,
            title = entity.title,
            url = entity.url,
            urlToImage = entity.urlToImage,
            source = entity.let { entitysource ->
                SourceUi(
                    id = entitysource.source_id,
                    name = entitysource.source_name
                )
            }
        )
    }
}