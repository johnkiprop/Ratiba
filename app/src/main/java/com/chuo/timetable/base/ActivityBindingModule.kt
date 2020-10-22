package com.chuo.timetable.base

import com.chuo.timetable.di.ActivityKey
import com.chuo.timetable.home.MainActivity
import com.chuo.timetable.home.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap


@Module(subcomponents = [MainActivityComponent::class])
 abstract class ActivityBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun provideMainActivityInjector(builder: MainActivityComponent.Builder?): AndroidInjector.Factory<*>?
}