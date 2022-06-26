package com.kongjak.koreatechcse.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kongjak.koreatechcse.R
import com.kongjak.koreatechcse.data.Board

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var dataList = mutableListOf<Board>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            var title = dataList[position].title
            val articleNum = dataList[position].article_num
            if (title != null) {
                if (dataList[position].num == "공지")
                    title = "[공지] $title"
                titleTextView.text = title
                writerTextView.text = dataList[position].writer
            } else {
                Log.d("Test", "END")
            }
            itemView.setOnClickListener {
                onClickListener.onClick(articleNum)
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.item_title)
        val writerTextView: TextView = itemView.findViewById(R.id.item_writer)
    }

    interface OnClickListener {
        fun onClick(position: String)
    }

    inline fun setOnClickListener(crossinline item: (String) -> Unit) {
        this.onClickListener = object : OnClickListener {
            override fun onClick(position: String) {
                item(position)
            }
        }
    }
}
