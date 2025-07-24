package com.example.newsapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.model.FavoriteArticle

class NewsAdapter(
    private val onItemClick: (Article) -> Unit,
    private val onFavoriteClick: (Article) -> Unit
) : ListAdapter<Article, NewsAdapter.NewsViewHolder>(DiffCallback()) {

    private var favoriteArticles: List<FavoriteArticle> = emptyList()

    fun setFavorites(favorites: List<FavoriteArticle>) { //nyimpen daftar berita yang di favoritkan
        favoriteArticles = favorites
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        private val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        private val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        private val buttonFavorite: ImageButton = itemView.findViewById(R.id.buttonFavorite)

        fun bind(article: Article) {
            textTitle.text = article.title
            textDescription.text = article.description

            Glide.with(itemView)
                .load(article.urlToImage)
                .placeholder(R.drawable.placeholder)
                .into(imageThumbnail)

            val isFavorite = favoriteArticles.any { it.url == article.url }
            buttonFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_red else R.drawable.ic_favorite_border
            )

            itemView.setOnClickListener {
                onItemClick(article)
            }

            buttonFavorite.setOnClickListener {
                onFavoriteClick(article)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}
