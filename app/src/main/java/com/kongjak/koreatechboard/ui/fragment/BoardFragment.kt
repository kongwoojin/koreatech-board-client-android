package com.kongjak.koreatechboard.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.adapter.BoardAdapter
import com.kongjak.koreatechboard.databinding.FragmentBoardBinding
import com.kongjak.koreatechboard.ui.viewmodel.BoardViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BoardFragment : Fragment() {

    private val boardAdapter = BoardAdapter()
    private lateinit var binding: FragmentBoardBinding
    private val boardViewModel: BoardViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_board, container, false)
        val rootView: View = binding.root

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = boardViewModel

        boardViewModel.setBoardData(requireArguments().getString("board")!!)
        boardViewModel.setSiteData(requireArguments().getString("site")!!)

        val dividerItemDecoration = DividerItemDecoration(
            binding.recyclerView.context,
            LinearLayoutManager(rootView.context).orientation
        )

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = boardAdapter
            addItemDecoration(dividerItemDecoration)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding.prevFab.visibility == View.VISIBLE) {
                    binding.prevFab.hide()
                } else if (dy < 0 && binding.prevFab.visibility != View.VISIBLE) {
                    if (boardViewModel.page.value!! > 1) {
                        binding.prevFab.show()
                    }
                }
                if (dy > 0 && binding.nextFab.visibility == View.VISIBLE) {
                    binding.nextFab.hide()
                } else if (dy < 0 && binding.nextFab.visibility != View.VISIBLE) {
                    binding.nextFab.show()
                }
            }
        })

        boardAdapter.setOnClickListener { url ->
            when (resources.getBoolean(R.bool.is_tablet)) {
                true -> {
                    val articleFragment = ArticleFragment()
                    val articleBundle = Bundle()
                    articleBundle.putString("site", boardViewModel.site.value)
                    articleBundle.putString("url", url)
                    articleFragment.arguments = articleBundle

                    parentFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.article_frame_layout, articleFragment)
                        .commit()
                }
                false -> {
                    val intent = Intent(context, ArticleActivity::class.java)
                    intent.putExtra("site", boardViewModel.site.value)
                    intent.putExtra("url", url)
                    startActivity(intent)
                }
            }
        }

        binding.prevFab.setOnClickListener {
            boardViewModel.prevPage()
        }

        binding.nextFab.setOnClickListener {
            boardViewModel.nextPage()
        }

        binding.swipeRefresh.setOnRefreshListener {

        }

        boardViewModel.initData()
        return rootView
    }
}