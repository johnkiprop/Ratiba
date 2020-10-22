package com.chuo.timetable.scheduler

import androidx.lifecycle.LiveData
import com.chuo.timetable.base.ViewState
import com.chuo.timetable.model.Schedule
import com.chuo.timetable.model.Teacher

class SchedulerViewState(
    var admin: Boolean = false,
    var listener: Boolean = false,
    newFragment: Boolean =false,
    var errorMessage: String = "",
    var itemsLiveData: LiveData<List<Schedule>>? = null

): ViewState(
    newFragment
)