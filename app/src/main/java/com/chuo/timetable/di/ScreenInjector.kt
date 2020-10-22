package com.chuo.timetable.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.chuo.timetable.base.BaseActivity
import com.chuo.timetable.base.BaseFragment
import dagger.android.AndroidInjector
import javax.inject.Inject
import javax.inject.Provider


@ActivityScope
 class ScreenInjector @Inject constructor(
    screenInjectors: MutableMap<Class<out Fragment?>?,
            @JvmSuppressWildcards Provider<AndroidInjector.Factory<out Fragment?>?>?>?) {
    private val screenInjectors: MutableMap<Class<out Fragment?>?, Provider<AndroidInjector.Factory<out Fragment?>?>?>? =
        screenInjectors
    private val cache: MutableMap<String?, AndroidInjector<Fragment?>?>? =
        HashMap()

    fun inject(fragment: Fragment?) {
        require(fragment is BaseFragment<*,*,*>) { "Fragment must extend BaseFragment" }
        val instanceId = fragment.getArguments()?.getString("instance_id");
        if (cache!!.containsKey(instanceId)) {
            cache[instanceId]!!.inject(fragment)
            return
        }
        val injectorFactory =
            screenInjectors!![fragment?.javaClass]?.get() as AndroidInjector.Factory<Fragment?>
        val injector = injectorFactory.create(fragment)
        cache[instanceId] = injector
        injector!!.inject(fragment)
    }

    fun clear(fragment: Fragment) {
            cache!!.remove(fragment.arguments?.getString("instance_id"))
    }

    companion object {
        @JvmName("getScreenInjector")
         operator fun get(activity: Activity?): ScreenInjector? {
            require(activity is BaseActivity) { "Fragment must be hosted by BaseActivity" }
            return (activity as BaseActivity?)!!.getInjector()
        }
    }

}
