package com.chuo.timetable.login

import com.chuo.timetable.base.ViewState


class LoginViewState(
    var submitEnabled:Boolean = true,
    var progress: Boolean = false,
    newFragment: Boolean = false,
    var errorMessage: String = "",
    var changePassMessage: String = ""
): ViewState(
    newFragment
)