package com.chuo.timetable.di

import android.app.Activity
import androidx.fragment.app.Fragment


object Injector {
    public fun inject(activity: Activity?) {
        ActivityInjector[activity!!]!!.inject(activity)
    }

    public fun clearComponent(activity: Activity?) {
        ActivityInjector[activity!!]!!.clear(activity)
    }
    public fun inject(fragment: Fragment) {
     ScreenInjector[fragment.activity]!!.inject(fragment)
    }

    public  fun clearComponent(fragment: Fragment) {
        ScreenInjector[fragment.activity]!!.clear(fragment)
    }
}