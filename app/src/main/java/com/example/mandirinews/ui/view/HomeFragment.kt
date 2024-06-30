package com.example.mandirinews.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mandirinews.databinding.FragmentHomeBinding
import com.example.mandirinews.di.Injection
import com.example.mandirinews.network.config.ApiResponse
import com.example.mandirinews.network.response.ArticlesItem
import com.example.mandirinews.ui.adapter.NewsAdapter
import com.example.mandirinews.ui.viewmodel.HomeViewModel
import com.example.mandirinews.utils.getElapsedTime
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsProvideRepository = Injection.provideRepository()
        val factory = HomeViewModel.HomeViewModelFactory(newsProvideRepository)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        newsAdapter = NewsAdapter()
        binding?.rvNewsList?.layoutManager = LinearLayoutManager(requireContext())
        binding?.rvNewsList?.adapter = newsAdapter
        lifecycleScope.launch {
            homeViewModel.newsFlow.collectLatest { pagingData ->
                newsAdapter.submitData(pagingData)
            }
        }
        newsAdapter.addLoadStateListener { loadState ->
            binding?.apply {
                // show a retry button outside the list when refresh hits an error
                shimmerNewsList.isVisible = loadState.refresh !is LoadState.Error

                // swipeRefreshLayout displays whether refresh is occurring
                shimmerNewsList.isVisible = loadState.refresh is LoadState.Loading

                // show an empty state over the list when loading initially, before items are loaded
                shimmerNewsList.isVisible = loadState.refresh is LoadState.Loading && newsAdapter.itemCount == 0
            }
        }
        homeViewModel.topHeadline.observe(viewLifecycleOwner) { result ->
            when(result){
                is ApiResponse.Success -> {
                    binding?.apply {
                        shimmerHeadline.stopShimmer()
                        shimmerHeadline.isVisible = false
                        tvHeadlineTitle.text = result.data?.title
                        tvHeadlineSource.text = result.data?.source?.name
                        tvHeadlineTime.text = result.data?.publishedAt?.let { getElapsedTime(it) }
                        ivHeadlinePhoto.let {
                            Glide.with(requireContext())
                                .load(result.data?.urlToImage)
                                .into(it)
                        }
                    }
                }
                is ApiResponse.Error -> {
                    binding?.apply {
                        shimmerHeadline.stopShimmer()
                        shimmerHeadline.isVisible = false
                    }
                }
                is ApiResponse.Empty -> {
                    binding?.apply {
                        shimmerHeadline.startShimmer()
                        shimmerHeadline.isVisible = true
                    }
                }
            }
        }
    }
}