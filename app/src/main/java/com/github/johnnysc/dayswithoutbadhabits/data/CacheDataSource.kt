package com.github.johnnysc.dayswithoutbadhabits.data

/**
 * @author Asatryan on 18.12.2022
 */
interface CacheDataSource {

    fun read(): MutableList<CardCache>
    fun save(list: MutableList<CardCache>)

    class Base(
        private val stringStorage: StringStorage,
        private val serialization: Serialization
    ) : CacheDataSource {

        override fun read(): MutableList<CardCache> {
            val empty = CacheListWrapper()
            val default = serialization.toJson(empty)
            val cache = stringStorage.read(KEY, default)
            val saved = serialization.fromJson(cache, CacheListWrapper::class.java)
            return saved.list
        }

        override fun save(list: MutableList<CardCache>) {
            val newItem = CacheListWrapper(list)
            val string = serialization.toJson(newItem)
            stringStorage.save(KEY, string)
        }

        companion object {
            private const val KEY = "cached cards"
        }
    }

    private data class CacheListWrapper(val list: MutableList<CardCache> = ArrayList())
}