package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences

/**
 * @author Asatryan on 15.12.2022
 */
interface CacheDataSource {

    fun save(time: Long)
    fun time(default: Long): Long

    class Base(private val sharedPreferences: SharedPreferences) : CacheDataSource {

        override fun save(time: Long) =
            sharedPreferences.edit().putLong(KEY, time).apply()

        override fun time(default: Long): Long =
            sharedPreferences.getLong(KEY, default)

        companion object {
            private const val KEY = "savedTimeKey"
        }
    }
}