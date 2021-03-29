package com.dyamschikov.newsapiapp.newsheadlines.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dyamschikov.newsapiapp.data.modelui.ArticleUi
import com.dyamschikov.newsapiapp.databinding.HeaderItemsBinding
import com.dyamschikov.newsapiapp.databinding.ItemHeadlinesNewsBinding
import com.dyamschikov.newsapiapp.newsheadlines.HeaderDataManager
import com.dyamschikov.newsapiapp.newsheadlines.presentation.adapter.NewsHeadlinesAdapter.NewsHeadlinesViewHolder.Companion.EXCLUDE_HEADER_POSITION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsHeadlinesAdapter :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(NewsHeadlinesDiffCallback()) {

    var onItemClick: ((item: Int) -> Unit)? = null
    var onItemHeaderClick: ((item: Int) -> Unit)? = null

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun addHeaderAndSubmitList(dataList: List<ArticleUi>?) {
        adapterScope.launch {
            val items = when (dataList) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + dataList.map { DataItem.NewsHeadlinesItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> NewsHeadlinesViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(onItemHeaderClick)
            is NewsHeadlinesViewHolder -> {
                val newsHeadlinesItem = getItem(position) as DataItem.NewsHeadlinesItem
                holder.bind(
                    newsHeadlinesItem.articleUi,
                    position - EXCLUDE_HEADER_POSITION,
                    onItemClick
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.NewsHeadlinesItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    /**HeaderHolder*/
    class HeaderViewHolder private constructor(val binding: HeaderItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onItemHeaderClick: ((item: Int) -> Unit)?) {

            val dataHeader = HeaderDataManager.getListCountry()
            val adapter = HeaderAdapter()
                .apply {
                onItemClick = { item ->
                    onItemHeaderClick?.invoke(item)
                }
            }
            adapter.setItems(dataHeader)
            binding.headerRv.adapter = adapter
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderItemsBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(binding)
            }
        }
    }

    /**DataHolder*/
    class NewsHeadlinesViewHolder private constructor(val binding: ItemHeadlinesNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: ArticleUi,
            position: Int,
            onItemClick: ((item: Int) -> Unit)?
        ) {
            binding.articleUi = item

            binding.root.setOnClickListener {
                onItemClick?.invoke(position)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewsHeadlinesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHeadlinesNewsBinding.inflate(layoutInflater, parent, false)
                return NewsHeadlinesViewHolder(binding)
            }

            const val EXCLUDE_HEADER_POSITION = 1
        }
    }

    companion object {
        private const val ITEM_VIEW_TYPE_HEADER = 0
        private const val ITEM_VIEW_TYPE_ITEM = 1
    }
}

class NewsHeadlinesDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    data class NewsHeadlinesItem(val articleUi: ArticleUi) : DataItem() {
        override val id = articleUi.title
    }
    object Header : DataItem() {
        override val id = "String.MIN_VALUE"
    }
    abstract val id: String?
}