package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.FavoriteViewModel

class HomeFragment : Fragment() {

    //binding untuk nge hubungin ke layout fragment_home.xml
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //adapter buat recyclerview
    private lateinit var newsAdapter: NewsAdapter
    //viewmodel buat ngambil berita
    private val newsViewModel: NewsViewModel by viewModels()
    //viewmodel buat ngelola berita favorit
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //inflate layout xml jadi tampilan nyata di layar
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            //persiapan recyclerview atau list list berita nya
        setupRecyclerView()

        //observasi perubahan data berita dari view model
        newsViewModel.news.observe(viewLifecycleOwner) { articles ->
            newsAdapter.submitList(articles)
        }
        //observasi perubahan data berita favorit
        favoriteViewModel.favoriteArticles.observe(viewLifecycleOwner) { favorites ->
            newsAdapter.setFavorites(favorites)
        }
        //observasi status loading
        newsViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            //kalau lagi loading, nanti nampilin progress bar
            binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        //minta berita dari api negara us
        newsViewModel.getHeadlines("us")
    }

    private fun setupRecyclerView() {
        //bikin adapter buat recyclerview
        newsAdapter = NewsAdapter(
            onItemClick = { article ->
                //kalau user nge klik berita, ngebuka detail activity
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("url", article.url) // Kirim URL ke DetailActivity
                }
                startActivity(intent)
            },
            onFavoriteClick = { article ->
                //kalu user ngeklik tombol favorit
                val favoriteArticle = FavoriteArticle(
                    title = article.title ?: "",
                    description = article.description ?: "",
                    urlToImage = article.urlToImage ?: "",
                    url = article.url ?: ""
                )
                //masukin atau ngehapus favorit di database
                favoriteViewModel.insertOrDeleteFavorite(favoriteArticle)
            }
        )

        binding.recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
