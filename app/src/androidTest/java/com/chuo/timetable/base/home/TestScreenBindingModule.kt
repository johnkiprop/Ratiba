package com.chuo.timetable.base.home

import androidx.fragment.app.Fragment
import com.chuo.timetable.di.FragmentKey
import com.chuo.timetable.scheduler.SchedulerComponent
import com.chuo.timetable.scheduler.SchedulerFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module(subcomponents = [SchedulerComponent::class])
abstract class TestScreenBindingModule {
    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @FragmentKey(SchedulerFragment::class)
    abstract fun bindingProductsInjector(builder: ProductsComponent.Builder?): AndroidInjector.Factory<out Fragment?>?
}