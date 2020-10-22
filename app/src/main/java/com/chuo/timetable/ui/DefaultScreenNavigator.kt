package com.chuo.timetable.ui


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chuo.timetable.R
import com.chuo.timetable.di.ActivityScope
import com.chuo.timetable.lifecycle.ActivityLifecycleTask
import com.chuo.timetable.login.LoginFragment
import com.chuo.timetable.newuser.NewTeacherFragment
import com.chuo.timetable.scheduler.SchedulerFragment
import com.chuo.timetable.timetabler.TimetablerFragment

import javax.inject.Inject


@ActivityScope
class DefaultScreenNavigator @Inject internal constructor() : ActivityLifecycleTask(), ScreenNavigator {

    private var fragmentManager: FragmentManager? = null
    override fun pop(): Boolean {
        return fragmentManager != null && fragmentManager!!.popBackStackImmediate();
    }
    override fun goToTimetabler()  {
        if (fragmentManager != null) {
            fragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                ?.replace(R.id.screen_container, TimetablerFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

    }

    override fun goToScheduler()  {
        if (fragmentManager != null) {
            fragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                ?.replace(R.id.screen_container, SchedulerFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

    }

    override fun goToLogin() {
        if (fragmentManager != null) {
            fragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                ?.replace(R.id.screen_container, LoginFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }

    }

    override fun goToNewTeacher() {
        if (fragmentManager != null) {
            fragmentManager?.beginTransaction()
                ?.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                ?.replace(R.id.screen_container, NewTeacherFragment.newInstance())
                ?.addToBackStack(null)
                ?.commit()
        }
    }


    override fun onDestroy(activity: AppCompatActivity?) {
        fragmentManager = null
    }
    override fun onCreate(activity: AppCompatActivity?) {
        init(activity!!.supportFragmentManager, (activity as ScreenProvider).initialScreen())
    }

    fun init(fragmentManager: FragmentManager, rootScreen: Fragment?) {
        this.fragmentManager = fragmentManager
        if (fragmentManager.fragments.size == 0) {
            rootScreen?.let {
                fragmentManager.beginTransaction()
                    .replace(R.id.screen_container, it)
                    .commit()
            }
        }
    }


}
