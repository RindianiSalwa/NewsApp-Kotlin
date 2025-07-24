package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.network.RetrofitClient
import com.example.newsapp.model.Article
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    //live data buat nyimpen daftar berita
    private val _news = MutableLiveData<List<Article>>() //private biar gak bisa diubah dari luar
    val news: LiveData<List<Article>> = _news //public yang bisa di ubah dari UI

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading //buat nampilin loading

    private val apiKey = "b01795a15a464e01b6df463d8d81c81f"
    //fungsi buat ngambil data berita dari api
    fun getHeadlines(country: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                //manggil retrofit buat ngambil data berita
                val response = RetrofitClient.instance.getTopHeadlines(country, apiKey)
                if (response.isSuccessful) {
                    //kalo sukses nyimpen data berita ke livedata
                    _news.value = response.body()?.articles ?: emptyList()
                } else {
                    _news.value = emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _news.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchNews(query: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.searchNews(query, apiKey)
                if (response.isSuccessful) {
                    _news.value = response.body()?.articles ?: emptyList()
                } else {
                    _news.value = emptyList()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _news.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
