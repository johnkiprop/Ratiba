package com.chuo.timetable.base

import com.chuo.timetable.coroutines.DispatcherModule
import com.chuo.timetable.repository.RepoModule
import com.chuo.timetable.viewmodel.CommonUIModule
import com.chuo.timetable.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ApplicationModule::class,
    ActivityBindingModule::class,
    CommonUIModule::class,
    ViewModelModule::class,
    DispatcherModule::class,
    ViewStatesModule::class,
    RepoModule::class
])
interface ApplicationComponent {
  fun inject(myApplication: MyApplication?)
}