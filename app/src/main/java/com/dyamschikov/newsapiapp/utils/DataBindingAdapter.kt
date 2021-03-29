package com.dyamschikov.newsapiapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dyamschikov.newsapiapp.R

@BindingAdapter("goneUnless")
fun goneUnless(view: View, status: Boolean) {
    view.visibility = if (status) View.VISIBLE else View.GONE
}

@BindingAdapter("loadThumbnail")
fun loadThumbnail(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .placeholder(R.drawable.ic_news_placeholder)
            .error(R.drawable.ic_error_image)
            .fallback(R.drawable.ic_news_placeholder)
            .into(imageView)
    } ?: imageView.setImageResource(R.drawable.ic_news_placeholder)
}