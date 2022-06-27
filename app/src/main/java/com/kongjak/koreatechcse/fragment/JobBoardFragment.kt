package com.kongjak.koreatechcse.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kongjak.koreatechcse.R
import com.kongjak.koreatechcse.activity.ArticleActivity
import com.kongjak.koreatechcse.adapter.BoardAdapter
import com.kongjak.koreatechcse.connection.RetrofitBuilder
import com.kongjak.koreatechcse.data.Board
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobBoardFragment : Fragment() {

    private val dataList = mutableListOf<Board>()
    private val boardAdapter = BoardAdapter()
    private var page = 1
    private lateinit var prevFab: FloatingActionButton
    private lateinit var nextFab: FloatingActionButton
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_job_board, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recycler_view)
        prevFab = rootView.findViewById(R.id.prev_fab)
        nextFab = rootView.findViewById(R.id.next_fab)
        swipeRefresh = rootView.findViewById(R.id.swipe_refresh)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager(rootView.context).orientation
        )

        getApi()

        boardAdapter.dataList = dataList
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = boardAdapter
            addItemDecoration(dividerItemDecoration)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && prevFab.visibility == View.VISIBLE) {
                    prevFab.hide()
                } else if (dy < 0 && prevFab.visibility != View.VISIBLE) {
                    if (page != 1) {
                        prevFab.show()
                    }
                }
                if (dy > 0 && nextFab.visibility == View.VISIBLE) {
                    nextFab.hide()
                } else if (dy < 0 && nextFab.visibility != View.VISIBLE) {
                    nextFab.show()

                }

            }
        })

        reloadFab()

        boardAdapter.setOnClickListener {
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra("board", "jobboard")
            intent.putExtra("article_num", it.toInt())
            startActivity(intent)
        }

        prevFab.setOnClickListener {
            page--
            getApi()
        }

        nextFab.setOnClickListener {
            page++
            getApi()
        }

        swipeRefresh.setOnRefreshListener {
            getApi()
        }
        return rootView
    }

     fun reloadFab() {
        if (page == 1)
            prevFab.hide()
        else
            prevFab.show()
    }

    private fun getApi() {
        RetrofitBuilder.api.getJobBoard(page).enqueue(object : Callback<ArrayList<Board>> {
            override fun onResponse(
                call: Call<ArrayList<Board>>,
                response: Response<ArrayList<Board>>
            ) {
                val list = response.body()
                dataList.clear()
                dataList.addAll(list!!)
                boardAdapter.notifyDataSetChanged()
                swipeRefresh.isRefreshing = false
                reloadFab()
            }

            override fun onFailure(call: Call<ArrayList<Board>>, t: Throwable) {
                Log.d("error", t.message.toString())
            }
        })
    }
}