package com.chuo.timetable.scheduler

import androidx.recyclerview.widget.DiffUtil
import com.chuo.timetable.model.Schedule


class RepoDiffCallback
    (private val oldList: List<Schedule>, private val newList: List<Schedule>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }
    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].description === newList[newItemPosition].description
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}
