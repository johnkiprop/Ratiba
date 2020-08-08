package com.example.soko.data

import com.example.soko.model.ProductItems

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class ProductItemsResponse(
    val items: List<ProductItems>
)