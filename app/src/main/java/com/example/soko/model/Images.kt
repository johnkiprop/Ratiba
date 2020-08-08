package com.example.soko.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "images")
data class Images(
        @field:PrimaryKey
        @NonNull
        @field:ColumnInfo(name = "md")
    val md:Long,
    @NonNull
    @field:ColumnInfo(name = "imn")
   val imn: String,
        @NonNull
    @field:ColumnInfo(name = "src")
     val src: String
)