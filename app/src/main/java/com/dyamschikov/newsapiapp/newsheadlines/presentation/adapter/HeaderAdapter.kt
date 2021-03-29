package com.dyamschikov.newsapiapp.newsheadlines.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dyamschikov.newsapiapp.databinding.HeaderItemBinding
import com.dyamschikov.newsapiapp.newsheadlines.Country

class HeaderAdapter :
    RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {

    var onItemClick: ((item: Int) -> Unit)? = null
    private val data = mutableListOf<Country>()

    fun setItems(data: List<Country>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, onItemClick, position)
    }

    override fun getItemCount(): Int = data.size

    class HeaderViewHolder private constructor(val binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Country, onItemClick: ((item: Int) -> Unit)?, position: Int) {

            binding.headerTitleTv.text = item.title
            Glide.with(binding.root.context).load(item.icon)
                .centerCrop().into(binding.headerIv)

            binding.headerCv.setOnClickListener {
                onItemClick?.invoke(position)
            }
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HeaderItemBinding.inflate(layoutInflater, parent, false)
                return HeaderViewHolder(
                    binding
                )
            }
        }
    }
}