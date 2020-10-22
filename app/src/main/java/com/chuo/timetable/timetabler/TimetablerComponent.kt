package com.chuo.timetable.timetabler

import com.chuo.timetable.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@ScreenScope
@Subcomponent()
interface TimetablerComponent: AndroidInjector<TimetablerFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<TimetablerFragment>() {

    }
}