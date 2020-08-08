package com.example.soko.data

import dagger.Binds
import dagger.Module

@Module
abstract class TestRepoServiceModule {
    @Binds
    abstract fun bindRepoService(repoService: TestRepoService):RepoService
}