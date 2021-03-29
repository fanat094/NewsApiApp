package com.dyamschikov.newsapiapp.data.modelui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SourceUi(
    val id: String?,
    val name: String?
) : Parcelable