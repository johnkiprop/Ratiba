package com.example.soko.base

import com.example.soko.base.home.TestMainActivityComponent
import com.example.soko.di.ActivityKey
import com.example.soko.home.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap

@Module(subcomponents = [TestMainActivityComponent::class])
abstract class TestActivityBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun provideMainActivityInjector(builder: TestMainActivityComponent.Builder?): AndroidInjector.Factory<*>?
}