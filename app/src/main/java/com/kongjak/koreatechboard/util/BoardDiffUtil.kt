package com.kongjak.koreatechboard.util

import androidx.recyclerview.widget.DiffUtil
import com.kongjak.koreatechboard.domain.model.BoardData

object BoardDiffUtil : DiffUtil.ItemCallback<BoardData>() {
    override fun areItemsTheSame(oldItem: BoardData, newItem: BoardData): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: BoardData, newItem: BoardData): Boolean {
        return oldItem == newItem
    }
}
