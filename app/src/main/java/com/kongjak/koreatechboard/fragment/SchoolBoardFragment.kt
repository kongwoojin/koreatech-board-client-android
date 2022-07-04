package com.kongjak.koreatechboard.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.activity.ArticleActivity
import com.kongjak.koreatechboard.adapter.SchoolBoardAdapter
import com.kongjak.koreatechboard.connection.RetrofitBuilder
import com.kongjak.koreatechboard.data.SchoolBoard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable


class SchoolBoardFragment : Fragment() {

    private val dataList = mutableListOf<SchoolBoard>()
    private val schoolBoardAdapter = SchoolBoardAdapter()
    private var page = 1
    private lateinit var prevFab: FloatingActionButton
    private lateinit var nextFab: FloatingActionButton
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView

    private lateinit var board: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(R.layout.fragment_cse_board, container, false)
        recyclerView = rootView.findViewById(R.id.recycler_view)
        prevFab = rootView.findViewById(R.id.prev_fab)
        nextFab = rootView.findViewById(R.id.next_fab)
        swipeRefresh = rootView.findViewById(R.id.swipe_refresh)
        progressBar = rootView.findViewById(R.id.progress_bar)

        board = requireArguments().getString("board")!!

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager(rootView.context).orientation
        )

        schoolBoardAdapter.dataList = dataList
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = schoolBoardAdapter
            addItemDecoration(dividerItemDecoration)
        }

        if (savedInstanceState != null) {
            val restoredData = savedInstanceState.getSerializable("data") as ArrayList<SchoolBoard>
            dataList.addAll(restoredData)
            page = savedInstanceState.getInt("page")
        } else {
            if (dataList.isEmpty())
                loadPage()
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

        schoolBoardAdapter.setOnClickListener { url ->
            val intent = Intent(context, ArticleActivity::class.java)
            intent.putExtra("site", "school")
            intent.putExtra("url", url)
            startActivity(intent)
        }

        prevFab.setOnClickListener {
            page--
            loadPage()
        }

        nextFab.setOnClickListener {
            page++
            loadPage()
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

    private fun loadPage() {
        progressBar.visibility = View.VISIBLE
        getApi()
    }

    private fun getApi() {
        RetrofitBuilder.api.getSchoolBoard(board, page)
            .enqueue(object : Callback<ArrayList<SchoolBoard>> {
                override fun onResponse(
                    call: Call<ArrayList<SchoolBoard>>,
                    response: Response<ArrayList<SchoolBoard>>
                ) {
                    val list = response.body()
                    dataList.clear()
                    dataList.addAll(list!!)
                    schoolBoardAdapter.notifyDataSetChanged()
                    progressBar.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                    reloadFab()
                }

                override fun onFailure(call: Call<ArrayList<SchoolBoard>>, t: Throwable) {
                    Log.d("error", t.message.toString())
                }
            })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("data", dataList as Serializable)
        outState.putInt("page", page)
    }
}