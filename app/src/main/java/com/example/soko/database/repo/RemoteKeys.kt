package com.example.soko.database.repo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val productId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)