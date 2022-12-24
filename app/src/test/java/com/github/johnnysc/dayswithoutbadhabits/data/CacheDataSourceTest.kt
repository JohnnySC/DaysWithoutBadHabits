package com.github.johnnysc.dayswithoutbadhabits.data

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 24.12.2022
 */
class CacheDataSourceTest {

    @Test
    fun `test read empty`() {
        val storage = FakeStringStorage()
        val dataSource = CacheDataSource.Base(storage, Serialization.Base())

        val actual = dataSource.read()
        val expected = emptyList<CardCache>().toMutableList()
        assertEquals(expected, actual)
    }

    @Test
    fun `test save and read`() {
        val storage = FakeStringStorage()
        val dataSource = CacheDataSource.Base(storage, Serialization.Base())

        dataSource.save(
            mutableListOf(
                CardCache(1L, 2L, "a"),
                CardCache(3L, 4L, "b")
            )
        )
        val actual = dataSource.read()
        val expected = mutableListOf(
            CardCache(1L, 2L, "a"),
            CardCache(3L, 4L, "b")
        )
        assertEquals(expected, actual)
    }
}

private class FakeStringStorage : StringStorage {
    private var string = ""

    override fun read(key: String, default: String): String =
        string.ifEmpty { default }

    override fun save(key: String, value: String) {
        this.string = value
    }
}