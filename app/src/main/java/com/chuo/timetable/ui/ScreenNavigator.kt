package com.chuo.timetable.ui



interface ScreenNavigator {
    fun pop(): Boolean
    fun goToLogin()
    fun goToNewTeacher()
    fun goToScheduler()
    fun goToTimetabler()
}