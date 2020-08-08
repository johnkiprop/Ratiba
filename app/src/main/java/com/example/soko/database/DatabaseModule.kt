package com.example.soko.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DatabaseModule {
    @Provides
    @Singleton
    open fun provideDatabase(context:Context): AppDatabase{
        return Room.databaseBuilder(context, AppDatabase::class.java, "Shopper.db").build()
    }
}