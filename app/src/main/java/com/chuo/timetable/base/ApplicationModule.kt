package com.chuo.timetable.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides


@Module
 class ApplicationModule constructor( val application: Application) {
    @Provides
    fun provideApplicationContext(): Context {
        return application
    }

}
