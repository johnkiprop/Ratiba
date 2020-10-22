package com.chuo.timetable.base

import com.chuo.timetable.login.LoginViewState
import com.chuo.timetable.newuser.NewTeacherViewState
import com.chuo.timetable.scheduler.SchedulerViewState
import com.chuo.timetable.timetabler.TimetablerViewState
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ViewStatesModule {
    @Provides
    @Singleton
    fun provideNewTeacherViewState(): NewTeacherViewState = NewTeacherViewState()


    @Provides
    @Singleton
    fun provideLoginViewState(): LoginViewState = LoginViewState()


    @Provides
    @Singleton
    fun provideSchedulerViewState(): SchedulerViewState = SchedulerViewState()

    @Provides
    @Singleton
    fun provideTimetablerViewState(): TimetablerViewState = TimetablerViewState()

}