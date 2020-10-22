package com.chuo.timetable.base.home

import com.chuo.timetable.di.ActivityScope
import com.chuo.timetable.home.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [TestScreenBindingModule::class])
interface TestMainActivityComponent: AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<MainActivity>() {

    }
}