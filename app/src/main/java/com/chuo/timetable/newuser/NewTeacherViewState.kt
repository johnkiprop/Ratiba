package com.chuo.timetable.newuser

import androidx.lifecycle.LiveData
import com.chuo.timetable.base.ViewState
import com.chuo.timetable.model.Teacher

class NewTeacherViewState(
    var submitEnabled: Boolean = true,
    var progress: Boolean = false,
    newFragment: Boolean = false,
    var errorMessage: String = "",
    var ackAdmin: String = "",
    var teachersLiveData: LiveData<List<Teacher>>? = null
): ViewState(
    newFragment
)