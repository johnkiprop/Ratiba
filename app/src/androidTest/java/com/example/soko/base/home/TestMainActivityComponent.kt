package com.example.soko.base.home

import com.example.soko.di.ActivityScope
import com.example.soko.home.MainActivity
import dagger.Subcomponent
import dagger.android.AndroidInjector

@ActivityScope
@Subcomponent(modules = [TestScreenBindingModule::class])
interface TestMainActivityComponent: AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<MainActivity>() {

    }
}