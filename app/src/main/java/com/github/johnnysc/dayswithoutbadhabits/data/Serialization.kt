package com.github.johnnysc.dayswithoutbadhabits.data

import com.google.gson.Gson

/**
 * @author Asatryan on 24.12.2022
 */
interface Serialization {

    fun <T> fromJson(json: String, clasz: Class<T>): T

    fun toJson(src: Any): String

    class Base(private val gson: Gson = Gson()) : Serialization {

        override fun <T> fromJson(json: String, clasz: Class<T>): T =
            gson.fromJson(json, clasz)

        override fun toJson(src: Any): String = gson.toJson(src)
    }
}