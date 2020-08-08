package com.example.soko.data


import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
open class RepoServiceModule {
    @Provides
    @Singleton
    open fun provideRepoService(retrofit: Retrofit): RepoService {
        return retrofit.create(RepoService::class.java)
    }


}

