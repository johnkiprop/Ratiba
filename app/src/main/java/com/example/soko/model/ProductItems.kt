package com.example.soko.model

import androidx.annotation.NonNull
import androidx.room.*

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "products")
data class ProductItems(
    @field:PrimaryKey
    @NonNull
    @field:ColumnInfo(name = "id")
    val id:Long,
    @NonNull
    @field:ColumnInfo(name = "name")
    val name: String,
    @field:ColumnInfo(name = "description")

    @NonNull
    val description: String,
    @Json(name="short_description")
    @field:ColumnInfo(name = "short_description")

    @NonNull
    val shortDescription:String,
    @field:ColumnInfo(name = "price")

    @NonNull
    val price: String,
    @field:ColumnInfo(name = "regular_price")
    @Json(name="regular_price")

    @NonNull
    val regularPrice: String,
    @field:ColumnInfo(name = "sale_price")
    @Json(name="sale_price")
    @NonNull
    val salePrice:String,
    @NonNull
    val images:MutableList<Images>

)