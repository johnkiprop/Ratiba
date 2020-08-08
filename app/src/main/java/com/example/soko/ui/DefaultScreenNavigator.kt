package com.example.soko.ui


import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.soko.R
import com.example.soko.di.ActivityScope
import com.example.soko.lifecycle.ActivityLifecycleTask
import javax.inject.Inject


@ActivityScope
class DefaultScreenNavigator @Inject internal constructor() : ActivityLifecycleTask(), ScreenNavigator {

    private var fragmentManager: FragmentManager? = null
    override fun pop(): Boolean {
        return fragmentManager != null && fragmentManager!!.popBackStackImmediate();
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


//


}
