package com.dyamschikov.newsapiapp.newsdetailsnews.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.databinding.ItemNewsDetailsBinding

class NewsDetailsAdapter:
    ListAdapter<ArticleUi, NewsDetailsAdapter.NewsDetailsViewHolder>(NewsDetailsDiffCallback()) {

    var onItemUrlClick: ((item: String?) -> Unit)? = null
    var onItemShareClick: ((item: String?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsDetailsViewHolder {
        val binding = ItemNewsDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsDetailsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class NewsDetailsViewHolder (private val binding: ItemNewsDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(articleUi: ArticleUi) {

            binding.articleUi = articleUi

            binding.newsDetailsReadMoreBtn.setOnClickListener {
                onItemUrlClick?.invoke(articleUi.url)
            }
            binding.newsDetailsShareBtn.setOnClickListener {
                onItemShareClick?.invoke(articleUi.url)
            }

            binding.executePendingBindings()
        }
    }
}

class NewsDetailsDiffCallback : DiffUtil.ItemCallback<ArticleUi>() {
    override fun areItemsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ArticleUi, newItem: ArticleUi): Boolean {
        return oldItem == newItem
    }
}