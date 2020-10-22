package com.chuo.timetable.scheduler

import com.chuo.timetable.di.ScreenScope
import dagger.BindsInstance
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ScreenScope
@Subcomponent()
interface SchedulerComponent: AndroidInjector<SchedulerFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<SchedulerFragment>() {

    }
}