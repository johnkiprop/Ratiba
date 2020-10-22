package com.chuo.timetable.test

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.chuo.timetable.base.TestApplication

class CustomTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, TestApplication::class.java.name, context)
    }
}