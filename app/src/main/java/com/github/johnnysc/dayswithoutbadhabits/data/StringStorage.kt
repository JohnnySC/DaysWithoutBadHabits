package com.github.johnnysc.dayswithoutbadhabits.data

import android.content.SharedPreferences

/**
 * @author Asatryan on 24.12.2022
 */
interface StringStorage {

    fun read(key: String, default: String): String

    fun save(key: String, value: String)

    class Base(private val preferences: SharedPreferences) : StringStorage {

        override fun read(key: String, default: String): String =
            preferences.getString(key, default) ?: default

        override fun save(key: String, value: String) {
            preferences.edit().putString(key, value).apply()
        }
    }
}