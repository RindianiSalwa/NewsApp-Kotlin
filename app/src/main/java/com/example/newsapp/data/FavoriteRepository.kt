package com.example.newsapp.data

import androidx.lifecycle.LiveData
import com.example.newsapp.model.FavoriteArticle

class FavoriteRepository(private val favoriteArticleDao: FavoriteArticleDao) {

    suspend fun insertFavorite(article: FavoriteArticle) {
        favoriteArticleDao.insertFavorite(article)
    }

    suspend fun deleteFavorite(article: FavoriteArticle) {
        favoriteArticleDao.deleteArticle(article)
    }

    fun getAllFavorites(): LiveData<List<FavoriteArticle>> {
        return favoriteArticleDao.getAllFavoriteArticles()
    }

    suspend fun getFavoriteByUrl(url: String): FavoriteArticle? {
        return favoriteArticleDao.getFavoriteByUrl(url)
    }
}
