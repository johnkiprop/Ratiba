package com.example.soko.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.soko.database.repo.RemoteKeys
import com.example.soko.database.repo.RemoteKeysDao
import com.example.soko.database.repo.RepoDao
import com.example.soko.model.ImageConverter
import com.example.soko.model.Images
import com.example.soko.model.ProductItems


@Database(
    entities = [ProductItems::class, Images::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ImageConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun reposDao(): RepoDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}