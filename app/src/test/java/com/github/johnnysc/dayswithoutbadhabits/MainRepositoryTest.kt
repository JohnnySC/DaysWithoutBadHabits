package com.github.johnnysc.dayswithoutbadhabits

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 15.12.2022
 */
class MainRepositoryTest {

    @Test
    fun no_days() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(1544)
        val actual = repository.days()
        val expected: Long = 0
        assertEquals(expected, actual)
        assertEquals(1544, cacheDataSource.time(-1))
    }

    @Test
    fun some_days() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        val day = 24 * 3600 * 1000
        cacheDataSource.save(2L * day)
        now.addTime(9L * day)

        val repository = MainRepository.Base(cacheDataSource, now)
        val actual = repository.days()
        val expected: Long = 7
        assertEquals(expected, actual)
    }

    @Test
    fun test_reset() {
        val cacheDataSource = FakeDataSource()
        val now = FakeNow.Base()
        val repository = MainRepository.Base(cacheDataSource, now)
        now.addTime(54321)
        repository.reset()
        assertEquals(54321, cacheDataSource.time(-1))
    }
}

private interface FakeNow : Now {

    fun addTime(diff: Long)

    class Base : FakeNow {

        private var time = 0L

        override fun time(): Long {
            return time
        }

        override fun addTime(diff: Long) {
            this.time += diff
        }
    }
}

private class FakeDataSource : CacheDataSource {

    private var value: Long = -100

    override fun save(time: Long) {
        this.value = time
    }

    override fun time(default: Long): Long {
        return if (value == -100L)
            default
        else
            value
    }
}