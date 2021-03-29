package com.dyamschikov.newsapiapp.newseverything.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dyamschikov.newsapiapp.R
import com.dyamschikov.newsapiapp.databinding.ItemLoadStateFooterViewBinding

class ReposLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ReposLoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ReposLoadStateViewHolder {
        return ReposLoadStateViewHolder.create(parent, retry)
    }

    override fun onBindViewHolder(holder: ReposLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }
}

class ReposLoadStateViewHolder(
    private val binding:
    ItemLoadStateFooterViewBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): ReposLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_load_state_footer_view, parent, false)
            val binding = ItemLoadStateFooterViewBinding.bind(view)
            return ReposLoadStateViewHolder(binding, retry)
        }
    }
}