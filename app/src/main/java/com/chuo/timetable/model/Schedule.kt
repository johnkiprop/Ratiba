package com.chuo.timetable.model


data class Schedule(
    var description: String? = "",
     var startTime: String? = "",
    var endTime: String? = "",
    var eventName: String? = "",
     var teacherMail:String? = ""
)