package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.network.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<Article>>()
    val searchResults: LiveData<List<Article>> = _searchResults

    fun searchNews(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.searchNews(query, apiKey)
                if (response.isSuccessful && response.body() != null) {
                    _searchResults.postValue(response.body()!!.articles)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
