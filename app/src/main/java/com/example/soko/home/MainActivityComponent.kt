package com.example.soko.home

import com.example.soko.di.ActivityScope
import com.example.soko.ui.NavigationModule
import dagger.Subcomponent
import dagger.android.AndroidInjector
@ActivityScope
@Subcomponent(modules = [
    MainScreenBindingModule::class,
    NavigationModule::class
])
public interface MainActivityComponent :AndroidInjector<MainActivity>{
@Subcomponent.Builder
abstract class Builder: AndroidInjector.Builder<MainActivity>() {

}
}