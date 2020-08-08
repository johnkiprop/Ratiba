package com.example.soko.networking

import dagger.Module
import dagger.Provides
import okhttp3.Call

import okhttp3.OkHttpClient
import javax.inject.Named

import javax.inject.Singleton




@Module
open class NetworkModule {
    @Provides
    @Singleton
    open fun provideOkHttp(): Call.Factory {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Named("base_url")
    open fun provideBaseUrl(): String {
        return "http://192.168.43.230/sokomobile/phone/"
    }
}