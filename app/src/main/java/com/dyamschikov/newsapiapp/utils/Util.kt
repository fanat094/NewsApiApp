package com.dyamschikov.newsapiapp.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun smartDate(dateApi: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = dateFormat.parse(dateApi)
    val formatter = SimpleDateFormat("d MMMM - HH:mm", Locale.getDefault())
    return formatter.format(date)
}

fun View.showSnackbar(message: CharSequence) =
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()