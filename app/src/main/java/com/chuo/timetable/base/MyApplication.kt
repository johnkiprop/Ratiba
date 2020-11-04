package com.chuo.timetable.base

import android.app.Application
import com.chuo.timetable.di.ActivityInjector
import timber.log.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree
import javax.inject.Inject


 open class MyApplication : Application(){
    @Inject lateinit var activityInjector: ActivityInjector
     protected var component: ApplicationComponent? = null
    override fun onCreate() {
        super.onCreate()
        component = initComponent()
        component!!.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
     protected open fun initComponent(): ApplicationComponent? {
         return  DaggerApplicationComponent.builder()
             .applicationModule(ApplicationModule(this))
             .build()
     }

   fun getAcInjector(): ActivityInjector? {
        return  activityInjector
    }


}