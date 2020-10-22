package com.chuo.timetable.base

import com.chuo.timetable.base.home.TestMainActivityComponent
import com.chuo.timetable.di.ActivityKey
import com.chuo.timetable.home.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [TestMainActivityComponent::class])
abstract class TestActivityBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun provideMainActivityInjector(builder: TestMainActivityComponent.Builder?): AndroidInjector.Factory<*>?
}