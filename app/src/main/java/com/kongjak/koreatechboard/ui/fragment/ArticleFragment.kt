package com.kongjak.koreatechboard.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.databinding.FragmentArticleBinding
import com.kongjak.koreatechboard.ui.viewmodel.ArticleViewModel
import com.kongjak.koreatechboard.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private val articleViewModel: ArticleViewModel by activityViewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_article, container, false)
        val rootView = binding.root

        articleViewModel.setSiteData(requireArguments().getString("site")!!)
        val uuid = UUID.fromString(requireArguments().getString("uuid")!!)
        articleViewModel.setUUIDData(uuid)

        binding.vm = articleViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        articleViewModel.getArticleData()

        articleViewModel.article.observe(viewLifecycleOwner) {
            mainViewModel.updateUrl(it.articleUrl)
            mainViewModel.updateMenuNeeded(true)
        }

        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
