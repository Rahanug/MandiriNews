package com.example.mandirinews.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mandirinews.R
import com.example.mandirinews.data.model.News
import com.example.mandirinews.databinding.FragmentDetailNewsBinding
import com.example.mandirinews.di.Injection
import com.example.mandirinews.ui.viewmodel.DetailViewModel

class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding
    private val args by navArgs<DetailNewsFragmentArgs>()
    private lateinit var detailViewModel: DetailViewModel
    private var isBookmarked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        checkIfBookmarked()
        val article = args.articlesItem
        binding?.apply {
            Glide.with(requireContext()).load(article.urlToImage).into(ivDetailPhoto)
            tvDetailTitle.text = article.title ?: ""
            tvDetailAuthor.text = article.author ?: ""
            tvDetailDescription.text = article.description ?: ""
            tvDetailContent.text = article.content ?: ""
            tvDetailSource.text = article.source?.name ?: ""
            tvDetailPublished.text = article.publishedAt ?: ""
        }
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar: androidx.appcompat.widget.Toolbar? = binding?.toolbar
        toolbar?.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.action_webview -> {
                    args.articlesItem.url?.let { url ->
                        val action =
                            DetailNewsFragmentDirections.actionDetailFragmentToWebviewFragment(url)
                        findNavController().navigate(action)
                    }
                    true
                }

                R.id.action_bookmark -> {
                    if (isBookmarked) {
                        removeBookmark()
                        updateToolbarMenu(toolbar)
                    } else {
                        addBookmark()
                        updateToolbarMenu(toolbar)
                    }
                    true
                }

                else -> false
            }
        }

    }

    private fun setupViewModel() {
        val newsProvideRepository = Injection.provideRepository(requireContext())
        val factory = DetailViewModel.DetailViewModelFactory(newsProvideRepository)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    private fun checkIfBookmarked() {
        val articleUrl = args.articlesItem.url ?: ""
        detailViewModel.isArticleBookmarked(articleUrl).observe(viewLifecycleOwner) { bookmarked ->
            isBookmarked = bookmarked
            activity?.invalidateOptionsMenu()
            binding?.toolbar?.menu?.findItem(R.id.action_bookmark)?.icon =
                if (isBookmarked) {
                    resources.getDrawable(R.drawable.baseline_bookmark_24_filled, null)
                } else {
                    resources.getDrawable(R.drawable.baseline_bookmark_24, null)
                }
        }
    }

    private fun updateToolbarMenu(toolbar: androidx.appcompat.widget.Toolbar?) {
        toolbar?.menu?.findItem(R.id.action_bookmark)?.icon =
            if (isBookmarked) {
                resources.getDrawable(R.drawable.baseline_bookmark_24_filled, null)
            } else {
                resources.getDrawable(R.drawable.baseline_bookmark_24, null)
            }
    }

    private fun addBookmark() {
        val newsEntity = News(
            url = args.articlesItem.url ?: "",
            publishedAt = args.articlesItem.publishedAt ?: "",
            author = args.articlesItem.author ?: "",
            urlToImage = args.articlesItem.urlToImage ?: "",
            description = args.articlesItem.description ?: "",
            sourceName = args.articlesItem.source?.name ?: "",
            title = args.articlesItem.title ?: "",
            content = args.articlesItem.content ?: ""
        )
        detailViewModel.saveArticle(newsEntity)
        isBookmarked = true
        activity?.invalidateOptionsMenu()
    }

    private fun removeBookmark() {
        detailViewModel.deleteArticle(args.articlesItem.url ?: "")
        isBookmarked = false
        activity?.invalidateOptionsMenu()
    }
}