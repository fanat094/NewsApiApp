package com.dyamschikov.newsapiapp.newseverything.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dyamschikov.newsapiapp.databinding.ItemEverythingNewsBinding
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi

class NewsEverythingAdapter :
    PagingDataAdapter<ArticleUi, NewsEverythingAdapter.NewsEverythingViewHolder>(NEWS_COMPARATOR) {

    var onItemClick: ((item: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsEverythingViewHolder {
        val binding =
            ItemEverythingNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsEverythingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsEverythingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class NewsEverythingViewHolder(private val binding: ItemEverythingNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleUi: ArticleUi) {
            binding.articleUi = articleUi
            binding.rowArticleContainer.setOnClickListener {
                onItemClick?.invoke(absoluteAdapterPosition)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        private val NEWS_COMPARATOR = object : DiffUtil.ItemCallback<ArticleUi>() {
            override fun areItemsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean =
                oldItem == newItem
        }
    }
}