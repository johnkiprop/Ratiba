package com.chuo.timetable.repository

import dagger.Binds
import dagger.Module
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
abstract class RepoModule {
    @ExperimentalCoroutinesApi
    @Binds
    abstract fun provideRepository(screenNavigator: FirebaseRepository?): Repository
}