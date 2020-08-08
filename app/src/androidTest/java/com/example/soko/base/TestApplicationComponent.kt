package com.example.soko.base

import com.example.soko.data.TestRepoServiceModule
import com.example.soko.networking.ServiceModule
import com.example.soko.ui.NavigationModule
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