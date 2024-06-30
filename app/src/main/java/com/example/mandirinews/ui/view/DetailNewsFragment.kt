package com.example.mandirinews.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mandirinews.R
import com.example.mandirinews.databinding.FragmentDetailNewsBinding

class DetailNewsFragment : Fragment() {
    private var _binding: FragmentDetailNewsBinding? = null
    private val binding get() = _binding
    private val args by navArgs<DetailNewsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                    // Handle saving the article to Room
//                    viewModel.saveArticle()
                    true
                }

                else -> false
            }
        }
    }
}