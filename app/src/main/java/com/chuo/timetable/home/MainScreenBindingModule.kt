package com.chuo.timetable.home

import androidx.fragment.app.Fragment
import com.chuo.timetable.di.FragmentKey
import com.chuo.timetable.login.LoginComponent
import com.chuo.timetable.login.LoginFragment
import com.chuo.timetable.newuser.NewTeacherComponent
import com.chuo.timetable.newuser.NewTeacherFragment
import com.chuo.timetable.scheduler.SchedulerComponent
import com.chuo.timetable.scheduler.SchedulerFragment
import com.chuo.timetable.timetabler.TimetablerComponent
import com.chuo.timetable.timetabler.TimetablerFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module(subcomponents = [NewTeacherComponent::class, LoginComponent::class,
    SchedulerComponent::class,TimetablerComponent::class])
public abstract class MainScreenBindingModule{
    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @FragmentKey(NewTeacherFragment::class)
    abstract fun bindingNewTeacherInjector(builder: NewTeacherComponent.Builder?): AndroidInjector.Factory<out Fragment?>?

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @FragmentKey(LoginFragment::class)
    abstract fun bindingLoginInjector(builder: LoginComponent.Builder?): AndroidInjector.Factory<out Fragment?>?

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @FragmentKey(SchedulerFragment::class)
    abstract fun bindingSchedulerInjector(builder: SchedulerComponent.Builder?): AndroidInjector.Factory<out Fragment?>?

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @FragmentKey(TimetablerFragment::class)
    abstract fun bindingTimetablerInjector(builder: TimetablerComponent.Builder?): AndroidInjector.Factory<out Fragment?>?
}