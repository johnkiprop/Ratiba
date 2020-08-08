package com.example.soko.model

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ImageJsonAdapter {
    @WrappedProductsList
    @FromJson
    fun fromJson(json: Image): List<Images> {
        return json.images
    }
    @ToJson
    fun toJson(@WrappedProductsList value: List<Images>): Image {
        throw UnsupportedOperationException()
    }
}