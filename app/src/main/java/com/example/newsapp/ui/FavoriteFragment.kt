package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.adapter.FavoriteAdapter
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.model.FavoriteArticle
import com.example.newsapp.viewmodel.FavoriteViewModel
import com.example.newsapp.viewmodel.FavoriteViewModelFactory

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var favoriteAdapter: FavoriteAdapter

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        // Observe perubahan data dari database
        favoriteViewModel.allFavorites.observe(viewLifecycleOwner) { favorites ->
            favoriteAdapter.submitList(favorites)
        }
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(
            onDeleteClick = { favoriteArticle ->
                favoriteViewModel.deleteFavorite(favoriteArticle)
            },
            onItemClick = { favoriteArticle ->
                val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                    putExtra("url", favoriteArticle.url)
                }
                startActivity(intent)
            }
        )

        binding.recyclerViewFavorite.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
