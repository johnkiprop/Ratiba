package com.chuo.timetable.scheduler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chuo.timetable.R
import com.chuo.timetable.model.Schedule


class RepoAdapter() : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {
    private val data: MutableList<Schedule> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_list_item, parent, false)
        return RepoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun setData(repos: List<Schedule>?) {
        if (repos != null) {
            val diffResult = DiffUtil.calculateDiff(RepoDiffCallback(data, repos))
            data.clear()
            data.addAll(repos)
            diffResult.dispatchUpdatesTo(this)
        } else {
            data.clear()
            notifyDataSetChanged()
        }
    }

   class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       private lateinit var time: TextView
       private lateinit var teacherSubject: TextView
        fun bind(schedule: Schedule) {
            time = itemView.findViewById(R.id.textview_time) as TextView
            teacherSubject = itemView.findViewById(R.id.textView_teacher_subject) as TextView
            var display = schedule.description
            time.text = "${schedule.startTime} - ${schedule.endTime}"
            teacherSubject.text = display
        }

    }


}
