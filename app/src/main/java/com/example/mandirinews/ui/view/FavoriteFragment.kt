package com.example.mandirinews.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mandirinews.databinding.FragmentFavoriteBinding
import com.example.mandirinews.di.Injection
import com.example.mandirinews.ui.adapter.FavoriteAdapter
import com.example.mandirinews.ui.viewmodel.FavoriteViewModel
import com.example.mandirinews.utils.toArticlesItem
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var newsAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViewModel() {
        val newsRepository = Injection.provideRepository(requireContext())
        val factory = FavoriteViewModel.FavoriteViewModelFactory(newsRepository)
        favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }

    private fun setupRecyclerView() {
        newsAdapter = FavoriteAdapter { articlesItem ->
            val action =
                FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(articlesItem)
            findNavController().navigate(action)
        }
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvFavorite?.adapter = newsAdapter
        lifecycleScope.launch {
            favoriteViewModel.favoriteNews.observe(viewLifecycleOwner) { favoriteNews ->
                val articlesItems = favoriteNews.map { it.toArticlesItem() }
                newsAdapter.submitList(articlesItems)
            }
        }
    }
}