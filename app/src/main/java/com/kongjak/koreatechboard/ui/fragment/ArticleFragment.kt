package com.kongjak.koreatechboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.databinding.FragmentArticleBinding
import com.kongjak.koreatechboard.ui.viewmodel.ArticleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val articleViewModel: ArticleViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_article, container, false)
        val rootView = binding.root

        articleViewModel.setSiteData(requireArguments().getString("site")!!)
        articleViewModel.setUrlData(requireArguments().getString("url")!!)

        binding.vm = articleViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        articleViewModel.getArticleData()

        return rootView
    }
}