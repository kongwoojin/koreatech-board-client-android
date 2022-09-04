package com.kongjak.koreatechboard.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kongjak.koreatechboard.databinding.ItemArticleBinding
import com.kongjak.koreatechboard.domain.model.BoardData
import com.kongjak.koreatechboard.util.BoardDiffUtil

class BoardAdapter : ListAdapter<BoardData, BoardAdapter.ViewHolder>(BoardDiffUtil) {

    lateinit var onClickListener: OnClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickListener)
    }

    override fun getItemCount(): Int = currentList.size

    class ViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            boardData: BoardData,
            onClickListener: OnClickListener,
        ) {
            binding.boardData = boardData

            binding.item.setOnClickListener {
                onClickListener.onClick(boardData.articleUrl)
            }
        }
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
