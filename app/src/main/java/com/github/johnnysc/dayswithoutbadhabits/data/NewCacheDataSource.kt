package com.github.johnnysc.dayswithoutbadhabits.data

import android.content.SharedPreferences
import com.google.gson.Gson

/**
 * @author Asatryan on 18.12.2022
 */
interface NewCacheDataSource {
    fun read(): MutableList<CardCache>
    fun save(list: MutableList<CardCache>)

    class Base(
        private val sharedPreferences: SharedPreferences,
        private val gson: Gson
    ) : NewCacheDataSource {

        override fun read(): MutableList<CardCache> {
            val empty = CacheListWrapper()
            val default = gson.toJson(empty)
            val cache = sharedPreferences.getString(KEY, default)
            val saved = gson.fromJson(cache, CacheListWrapper::class.java)
            return saved.list
        }

        override fun save(list: MutableList<CardCache>) {
            val newItem = CacheListWrapper(list)
            val string = gson.toJson(newItem)
            sharedPreferences.edit().putString(KEY, string).apply()
        }

        companion object {
            private const val KEY = "cached cards"
        }
    }

    private data class CacheListWrapper(val list: MutableList<CardCache> = ArrayList())
}