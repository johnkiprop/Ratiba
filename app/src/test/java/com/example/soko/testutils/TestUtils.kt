package com.example.soko.testutils

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.IOException
import org.robolectric.config.ConfigurationRegistry.instance
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.lang.reflect.Type


 open class TestUtils {
    private val TEST_MOSHI = initializeMoshi()
   fun <T> loadJson(path: String, type: Type): T? {
        return try {
            val json = getJson(path)
            TEST_MOSHI.adapter<Any>(type).fromJson(json) as T?
        } catch (e: IOException) {
            throw IllegalArgumentException("Could not deserialize: $path into type: $type")
        }
    }

   fun <T> loadJson(path: String, clazz: Class<T>): T? {
        return try {
            val json = getJson(path)
            TEST_MOSHI.adapter(clazz).fromJson(json)
        } catch (e: IOException) {
            throw IllegalArgumentException("Could not deserialize: $path into class: $clazz")
        }
    }
     fun getJson(path : String) : String {
         // Load the JSON response
         val uri = this.javaClass.classLoader?.getResource(path)
         val file = File(uri?.path)
         return String(file.readBytes())
     }
    private fun getFileString(path: String): String {
        return try {
            val sb = StringBuilder()
            val reader = BufferedReader(
                InputStreamReader(
                    this.javaClass.classLoader!!.getResourceAsStream(path)
                )
            )
           lateinit var line: String
            while (reader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            sb.toString()
        } catch (e: IOException) {
            throw IllegalArgumentException("Could not read from resource at: $path")
        }
    }

    private fun initializeMoshi(): Moshi {
        val builder = Moshi.Builder()
        builder.add(
            KotlinJsonAdapterFactory())
//        builder.add(ZonedDateTimeAdapter())
        return builder.build()
    }
}
