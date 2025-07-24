package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.viewmodel.NewsViewModel
import com.example.newsapp.viewmodel.FavoriteViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private val viewModel: NewsViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()


        viewModel.searchNews("android")


        viewModel.news.observe(viewLifecycleOwner) { articles ->
            newsAdapter.submitList(articles)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarSearch.visibility = if (isLoading) View.VISIBLE else View.GONE
        }


        binding.searchView.setIconifiedByDefault(false)
        binding.searchView.isIconified = false
        binding.searchView.clearFocus() // biar tidak langsung munculin keyboard
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter(
            onItemClick = { article ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("url", article.url)
                }
                startActivity(intent)
            },
            onFavoriteClick = { article ->
                val favoriteArticle = FavoriteArticle(
                    title = article.title ?: "",
                    description = article.description ?: "",
                    urlToImage = article.urlToImage ?: "",
                    url = article.url ?: ""
                )
                favoriteViewModel.insertOrDeleteFavorite(favoriteArticle)
            }
        )

        binding.recyclerViewSearch.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }

        favoriteViewModel.favoriteArticles.observe(viewLifecycleOwner) { favorites ->
            newsAdapter.setFavorites(favorites)
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.searchNews(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.searchNews(newText)
                } else {
                    viewModel.searchNews("android")
                }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
