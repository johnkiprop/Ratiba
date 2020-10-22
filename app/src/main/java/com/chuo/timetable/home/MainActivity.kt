package com.chuo.timetable.home

import androidx.fragment.app.Fragment
import com.chuo.timetable.R
import com.chuo.timetable.base.BaseActivity
import com.chuo.timetable.scheduler.SchedulerFragment


class MainActivity : BaseActivity(){
   override fun initialScreen(): Fragment? {
        return SchedulerFragment.newInstance()
    }

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }


}
