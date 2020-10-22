package com.chuo.timetable.newuser

import com.chuo.timetable.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ScreenScope
@Subcomponent()
interface NewTeacherComponent: AndroidInjector<NewTeacherFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<NewTeacherFragment>() {

    }
}