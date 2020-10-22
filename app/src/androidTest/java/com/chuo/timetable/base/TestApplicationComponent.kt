package com.chuo.timetable.base

import com.chuo.timetable.data.TestRepoServiceModule
import com.chuo.timetable.networking.ServiceModule
import com.chuo.timetable.ui.NavigationModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
                      TestActivityBindingModule::class,
                      TestRepoServiceModule::class,
                      ServiceModule::class,
                      NavigationModule::class])
interface TestApplicationComponent: ApplicationComponent{

}