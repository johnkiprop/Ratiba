package com.chuo.timetable.login

import com.chuo.timetable.di.ScreenScope
import dagger.Subcomponent
import dagger.android.AndroidInjector
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ScreenScope
@Subcomponent()
interface LoginComponent: AndroidInjector<LoginFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<LoginFragment>() {

    }
}