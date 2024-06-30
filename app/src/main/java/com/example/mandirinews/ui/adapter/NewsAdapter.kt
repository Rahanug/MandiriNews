package com.example.mandirinews.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mandirinews.R
import com.example.mandirinews.network.response.ArticlesItem
import com.example.mandirinews.utils.getElapsedTime

class NewsAdapter(private val onItemClicked: (ArticlesItem) -> Unit) :
    PagingDataAdapter<ArticlesItem, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
        holder.itemView.setOnClickListener {
            article?.let { onItemClicked(it) }
        }
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.tv_item_name)
        private val publishedAt: TextView = view.findViewById(R.id.tv_item_publishedAt)
        private val source: TextView = view.findViewById(R.id.tv_item_source)
        private val ivItemPhoto: ImageView = view.findViewById(R.id.iv_item_photo)
        fun bind(article: ArticlesItem?) {
            title.text = article?.title ?: "No Title"
            publishedAt.text = article?.publishedAt?.let { getElapsedTime(it) }
            source.text = article?.source?.name ?: "No Source"
            Glide.with(itemView.context)
                .load(article?.urlToImage)
                .into(ivItemPhoto)
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
            return oldItem == newItem
        }
    }
}