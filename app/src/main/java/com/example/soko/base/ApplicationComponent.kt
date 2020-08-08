package com.example.soko.base

import com.example.soko.data.RepoServiceModule
import com.example.soko.database.DatabaseModule
import com.example.soko.networking.ServiceModule
import com.example.soko.viewmodel.CommonUIModule
import com.example.soko.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    ApplicationModule::class,
    ActivityBindingModule::class,
    ServiceModule::class,
    RepoServiceModule::class,
    CommonUIModule::class,
    DatabaseModule::class,
    ViewModelModule::class

])
interface ApplicationComponent {
  fun inject(myApplication: MyApplication?)
}