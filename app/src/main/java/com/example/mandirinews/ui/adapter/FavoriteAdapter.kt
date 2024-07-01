package com.example.mandirinews.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mandirinews.databinding.ItemNewsBinding
import com.example.mandirinews.network.response.ArticlesItem
import com.example.mandirinews.utils.getElapsedTime

class FavoriteAdapter(private val onItemClicked: (ArticlesItem) -> Unit) :
    ListAdapter<ArticlesItem, FavoriteAdapter.FavoriteViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            article?.let { onItemClicked(it) }
        }
    }

    class FavoriteViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ArticlesItem) {
            binding.apply {
                tvItemName.text = article.title ?: "No Title"
                tvItemPublishedAt.text = article.publishedAt?.let { getElapsedTime(it) }
                tvItemSource.text = article.source?.name ?: "No Source"
                Glide.with(itemView.context)
                    .load(article.urlToImage)
                    .into(ivItemPhoto)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }
}