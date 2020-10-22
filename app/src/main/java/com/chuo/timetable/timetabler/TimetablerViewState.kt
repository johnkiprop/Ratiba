package com.chuo.timetable.timetabler

import androidx.lifecycle.LiveData
import com.chuo.timetable.base.ViewState
import com.chuo.timetable.model.Teacher
import com.chuo.timetable.model.TeacherMail


class TimetablerViewState(
    var submitEnabled:Boolean = true,
    var submitFinishEnabled:Boolean = true,
    var updatedSchedule:Boolean = false,
    newFragment: Boolean = false,
    var errorMessage: String = "",
    var updatedMessage: String = "",
    var teachersLiveData: LiveData<List<Teacher>>? = null,
    var teachersMailList: List<TeacherMail?> = arrayListOf(),
    var scheduleList:ArrayList<String?> = arrayListOf()
): ViewState(
    newFragment
)