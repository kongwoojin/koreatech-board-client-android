package com.kongjak.koreatechboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.databinding.FragmentArticleBinding
import com.kongjak.koreatechboard.ui.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_article, container, false)
        val rootView = binding.root

        articleViewModel.setSiteData(requireArguments().getString("site")!!)
        articleViewModel.setUrlData(requireArguments().getString("url")!!)

        binding.vm = articleViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        articleViewModel.getArticleData()

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
