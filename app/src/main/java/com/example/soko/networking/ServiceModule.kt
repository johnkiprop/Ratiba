package com.example.soko.networking

import com.example.soko.model.ImageJsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Call;
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import javax.inject.Named
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
open class ServiceModule {
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)
    @Provides
    @Singleton
    open fun provideMoshi(): Moshi {
        return Moshi.Builder()
            // ... add your own JsonAdapters and factories ...

            .add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    open fun provideRetrofit(
        moshi: Moshi,
        callFactory: Call.Factory,
        @Named("base_url") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .callFactory(callFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .baseUrl(baseUrl)
            .client(okHttp.build())
            .build()

    }
}