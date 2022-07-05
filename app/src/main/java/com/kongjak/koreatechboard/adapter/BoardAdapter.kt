package com.kongjak.koreatechboard.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kongjak.koreatechboard.R
import com.kongjak.koreatechboard.data.Board

class BoardAdapter : RecyclerView.Adapter<BoardAdapter.ViewHolder>() {

    var dataList = mutableListOf<Board>()
    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val title = dataList[position].title
            val noticeType = dataList[position].notice_type
            val num = dataList[position].num

            var itemTitle = title
            val noticeString = itemView.resources.getString(R.string.notice)

            if (title != null) {
                if (noticeType != null) {
                    itemTitle = "[$noticeType] $itemTitle"
                }

                itemTitle = if (num!!.isNumber()) {
                    "$num | $itemTitle"
                } else {
                    "$noticeString | $itemTitle"
                }

                titleTextView.text = itemTitle
                writerTextView.text = dataList[position].writer
            } else {
                Log.d("Test", "END")
            }
            itemView.setOnClickListener {
                onClickListener.onClick(dataList[position].article_url)
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

    private fun String.isNumber(): Boolean {
        return this.toIntOrNull() != null
    }
}
