package com.dyamschikov.newsapiapp.newsheadlines

import androidx.annotation.DrawableRes
import com.dyamschikov.newsapiapp.R

data class Country(
    val title: String,
    @DrawableRes
    val icon: Int
)

object HeaderDataManager {
    fun getListCountry() = listOf(
        Country(
            "Ukraine",
            R.drawable.ic_flag_ua
        ),
        Country(
            "USA",
            R.drawable.ic_flag_usa
        ),
        Country(
            "Great Britain",
            R.drawable.ic_flag_gb
        ),
        Country(
            "Poland",
            R.drawable.ic_flag_pl
        ),
        Country(
            "South Africa",
            R.drawable.ic_flag_za
        )
    )
}