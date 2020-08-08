package com.example.soko.model

import android.util.Log
import androidx.room.TypeConverter


class ImageConverter {
   @TypeConverter
   fun imagesToString(images:MutableList<Images>): String{
       return images.joinToString(separator = SEPARATOR) {it.md; it.imn; it.src}
   }
    @TypeConverter
    fun stringToImages(image:String): MutableList<Images> {
        return image.split(SEPARATOR).map { Images(it[0].toLong(), it[1].toString(), it)}.toMutableList()
    }

    companion object {
        private const val SEPARATOR = ","
    }
}