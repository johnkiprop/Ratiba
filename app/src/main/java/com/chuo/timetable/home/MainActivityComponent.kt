package com.chuo.timetable.home

import com.chuo.timetable.di.ActivityScope
import com.chuo.timetable.ui.NavigationModule
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