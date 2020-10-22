package com.chuo.timetable.viewmodel

import androidx.lifecycle.ViewModel
import com.chuo.timetable.login.LoginViewModel
import com.chuo.timetable.newuser.NewTeacherViewModel
import com.chuo.timetable.scheduler.SchedulerViewModel
import com.chuo.timetable.timetabler.TimetablerViewModel
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@AssistedModule
@Module(includes=[AssistedInject_ViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewTeacherViewModel::class)
    abstract fun bindNewTeacherViewModel(f: NewTeacherViewModel.Factory): AssistedSavedStateViewModelFactory< out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(SchedulerViewModel::class)
    abstract fun bindSchedulerViewModel(f: SchedulerViewModel.Factory): AssistedSavedStateViewModelFactory< out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(f: LoginViewModel.Factory): AssistedSavedStateViewModelFactory< out ViewModel>

    @Binds
    @IntoMap
    @ViewModelKey(TimetablerViewModel::class)
    abstract fun bindTimetablerViewModel(f: TimetablerViewModel.Factory): AssistedSavedStateViewModelFactory< out ViewModel>

}