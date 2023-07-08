package com.kongjak.koreatechboard.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.databinding.FragmentBoardBinding
import com.kongjak.koreatechboard.ui.activity.ArticleActivity
import com.kongjak.koreatechboard.ui.adapter.BoardAdapter
import com.kongjak.koreatechboard.ui.article.ArticleScreen
import com.kongjak.koreatechboard.ui.viewmodel.BoardViewModel
import com.kongjak.koreatechboard.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardFragment : Fragment() {

    private val boardAdapter = BoardAdapter()
    private var _binding: FragmentBoardBinding? = null
    private val binding get() = _binding!!

    private val boardViewModel: BoardViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_board, container, false)
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
                    if (boardViewModel.lastPage.value!! > boardViewModel.page.value!!) {
                        binding.nextFab.show()
                    }
                }
            }
        })

        boardAdapter.setOnClickListener { uuid ->
            when (resources.getBoolean(R.bool.is_tablet)) {
                true -> {
                    binding.articleComposeView?.apply {
                        setContent {
                            ArticleScreen(site = boardViewModel.site.value!!, uuid = uuid)
                        }
                    }
                }

                false -> {
                    val intent = Intent(context, ArticleActivity::class.java)
                    intent.putExtra("site", boardViewModel.site.value)
                    intent.putExtra("uuid", uuid.toString())
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
            boardViewModel.getApi()
        }

        boardViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        boardViewModel.initData()
        if (resources.getBoolean(R.bool.is_tablet)) {
            mainViewModel.updateMenuNeeded(false)
        }
        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
