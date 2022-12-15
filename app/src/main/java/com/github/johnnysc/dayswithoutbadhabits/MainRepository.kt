package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences

/**
 * @author Asatryan on 15.12.2022
 */
interface MainRepository {
    fun reset()
    fun days(): Int

    class Base(
        private val cacheDataSource: CacheDataSource,
        private val now: Now
    ) : MainRepository {

        override fun reset() =
            cacheDataSource.save(now.time())

        override fun days(): Int {
            val saved = cacheDataSource.time(-1)
            return if (saved == -1L) {
                reset()
                0
            } else {
                val diff = now.time() - saved
                (diff / DAY_MILLIS).toInt()
            }
        }

        companion object {
            private const val DAY_MILLIS = 24 * 3600 * 1000
        }
    }
}

interface Now {
    fun time(): Long

    class Base : Now {
        override fun time() = System.currentTimeMillis()
    }
}

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