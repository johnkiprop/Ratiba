package com.chuo.timetable.di

import android.app.Activity
import android.content.Context
import com.chuo.timetable.base.BaseActivity
import com.chuo.timetable.base.MyApplication
import dagger.android.AndroidInjector
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

 class ActivityInjector @Inject constructor( val activityInjectors: Map<Class<out Activity>,
         @JvmSuppressWildcards Provider<AndroidInjector.Factory<*>>>) {
    private val cache: MutableMap<String?, AndroidInjector<*>> =
        HashMap()

    fun inject(activity: Activity) {
        require(activity is BaseActivity) { "Activity must extend BaseActivity" }
        val instanceId = activity.getInstanceId()
        if (cache.containsKey(instanceId)) {
            (cache[instanceId] as AndroidInjector<Activity>).inject(activity)
            return
        }
        val injectorFactory =
            activityInjectors[activity.javaClass]?.get() as AndroidInjector.Factory<Activity>
        val injector = injectorFactory.create(activity)
        cache[instanceId] = injector
        injector.inject(activity)
    }

    fun clear(activity: Activity?) {
        require(activity is BaseActivity) { "Activity must extend BaseActivity" }
        cache.remove(activity.getInstanceId())
    }

    companion object {
        operator fun get(context: Context): ActivityInjector? {
            return (context.applicationContext as MyApplication).getAcInjector()
        }
    }

}

