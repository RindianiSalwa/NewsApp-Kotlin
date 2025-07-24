package com.example.newsapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.FavoriteDatabase
import com.example.newsapp.data.FavoriteRepository
import com.example.newsapp.model.FavoriteArticle
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FavoriteRepository
    val allFavorites: LiveData<List<FavoriteArticle>>
    val favoriteArticles: LiveData<List<FavoriteArticle>>

    init {
        val dao = FavoriteDatabase.getDatabase(application).favoriteArticleDao()
        repository = FavoriteRepository(dao)
        allFavorites = repository.getAllFavorites()
        favoriteArticles = allFavorites
    }

    fun insertFavorite(article: FavoriteArticle) {
        viewModelScope.launch {
            repository.insertFavorite(article)
        }
    }

    fun deleteFavorite(article: FavoriteArticle) {
        viewModelScope.launch {
            repository.deleteFavorite(article)
        }
    }

    fun insertOrDeleteFavorite(favoriteArticle: FavoriteArticle) {
        viewModelScope.launch {
            val existing = repository.getFavoriteByUrl(favoriteArticle.url)
            if (existing != null) {
                repository.deleteFavorite(existing)
            } else {
                repository.insertFavorite(favoriteArticle)
            }
        }
    }
}
