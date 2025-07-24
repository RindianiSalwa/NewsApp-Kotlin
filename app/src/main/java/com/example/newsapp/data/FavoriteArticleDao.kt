package com.example.newsapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsapp.model.FavoriteArticle

@Dao
interface FavoriteArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(article: FavoriteArticle)

    @Delete
    suspend fun deleteArticle(article: FavoriteArticle)

    @Query("SELECT * FROM favorite_articles")
    fun getAllFavoriteArticles(): LiveData<List<FavoriteArticle>>

    @Query("SELECT * FROM favorite_articles WHERE url = :url LIMIT 1")
    suspend fun getFavoriteByUrl(url: String): FavoriteArticle?
}
