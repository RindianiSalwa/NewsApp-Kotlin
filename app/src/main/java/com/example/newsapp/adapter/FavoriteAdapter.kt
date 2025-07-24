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
import com.example.newsapp.model.FavoriteArticle

class FavoriteAdapter(
    private val onDeleteClick: (FavoriteArticle) -> Unit,
    private val onItemClick: (FavoriteArticle) -> Unit
) : ListAdapter<FavoriteArticle, FavoriteAdapter.FavoriteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteArticle>() {
            override fun areItemsTheSame(oldItem: FavoriteArticle, newItem: FavoriteArticle): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: FavoriteArticle, newItem: FavoriteArticle): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageThumbnail: ImageView = itemView.findViewById(R.id.imageThumbnail)
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
        val buttonDelete: ImageButton = itemView.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteArticle = getItem(position)

        holder.textTitle.text = favoriteArticle.title
        holder.textDescription.text = favoriteArticle.description

        Glide.with(holder.itemView)
            .load(favoriteArticle.urlToImage)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageThumbnail)

        holder.buttonDelete.setOnClickListener {
            onDeleteClick(favoriteArticle)
        }


        holder.itemView.setOnClickListener {
            onItemClick(favoriteArticle)
        }
    }
}
