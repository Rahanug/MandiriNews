package com.example.mandirinews.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mandirinews.databinding.FragmentHomeBinding
import com.example.mandirinews.di.Injection
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
        homeViewModel.topHeadline.observe(viewLifecycleOwner) { article ->
            binding?.tvHeadlineTitle?.text = article?.title
            binding?.tvHeadlineSource?.text = article?.source?.name
            binding?.tvHeadlineTime?.text = article?.publishedAt?.let { getElapsedTime(it) }
            binding?.ivHeadlinePhoto?.let {
                Glide.with(requireContext())
                    .load(article?.urlToImage)
                    .into(it)
            }
        }

    }
}