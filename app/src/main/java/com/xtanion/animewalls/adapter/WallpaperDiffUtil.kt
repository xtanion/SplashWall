package com.xtanion.animewalls.adapter

import androidx.recyclerview.widget.DiffUtil
import com.xtanion.animewalls.data.WallData

class WallpaperDiffUtil(
    private val oldList: List<WallData>,
    private val newList: List<WallData>
):DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].created_at == newList[newItemPosition].created_at -> true
            oldList[oldItemPosition].height == newList[newItemPosition].height -> true
            oldList[oldItemPosition].width == newList[newItemPosition].width -> true
            else -> false
        }
    }
}