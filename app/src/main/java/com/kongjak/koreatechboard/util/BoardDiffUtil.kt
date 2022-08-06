package com.kongjak.koreatechboard.util

import androidx.recyclerview.widget.DiffUtil
import com.kongjak.koreatechboard.domain.model.Board

object BoardDiffUtil : DiffUtil.ItemCallback<Board>() {
    override fun areItemsTheSame(oldItem: Board, newItem: Board): Boolean {
        return oldItem.articleUrl == newItem.articleUrl
    }

    override fun areContentsTheSame(oldItem: Board, newItem: Board): Boolean {
        return oldItem == newItem
    }
}
