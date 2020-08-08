package com.example.soko.base

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
 class ApplicationModule constructor( val application: Application) {
    @Provides
    fun provideApplicationContext(): Context {
        return application
    }

}
