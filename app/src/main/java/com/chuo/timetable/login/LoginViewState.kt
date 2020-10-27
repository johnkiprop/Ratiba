package com.chuo.timetable.login

import androidx.lifecycle.LiveData
import com.chuo.timetable.base.ViewState
import com.chuo.timetable.model.Teacher


class LoginViewState(
    var submitEnabled:Boolean = true,
    var progress: Boolean = false,
    newFragment: Boolean = false,
    var errorMessage: String = "",
    var changePassMessage: String = "",
    var teachersLiveData: LiveData<List<Teacher>>? = null

): ViewState(
    newFragment
)