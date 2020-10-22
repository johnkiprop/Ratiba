package com.chuo.timetable.ui

import androidx.fragment.app.Fragment

interface ScreenProvider {
    fun initialScreen(): Fragment?
}