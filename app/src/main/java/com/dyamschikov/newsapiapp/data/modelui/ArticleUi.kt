package com.dyamschikov.newsapiapp.data.modelui

import android.os.Parcelable
import com.dyamschikov.newsapiapp.utils.smartDate
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleUi(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: SourceUi,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Parcelable {

    fun getArticleTitle() = title?.substringBeforeLast("|").toString()
    fun getDate() = publishedAt?.let { smartDate(it) }
    fun getSource() = source.name
    fun isDescriptionEmpty() = description.isNullOrEmpty()
}