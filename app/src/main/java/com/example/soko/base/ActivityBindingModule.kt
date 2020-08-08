package com.example.soko.base

import android.app.Activity
import com.example.soko.di.ActivityKey
import com.example.soko.home.MainActivity
import com.example.soko.home.MainActivityComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap


@Module(subcomponents = [MainActivityComponent::class])
 abstract class ActivityBindingModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    abstract fun provideMainActivityInjector(builder: MainActivityComponent.Builder?): AndroidInjector.Factory<*>?
}