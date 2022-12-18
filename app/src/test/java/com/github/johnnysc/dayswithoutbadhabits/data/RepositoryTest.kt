package com.github.johnnysc.dayswithoutbadhabits.data

import com.github.johnnysc.dayswithoutbadhabits.BaseTest
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 18.12.2022
 */
class RepositoryTest : BaseTest() {

    @Test
    fun `test no cards`() {
        val now = FakeNow.Base()
        val cacheDataSource = FakeCacheDataSource(emptyList())
        val repository = NewRepository(cacheDataSource, now)

        val actual: List<Card> = repository.cards()
        val expected = emptyList<Card>()
        assertEquals(expected, actual)
    }

    @Test
    fun `test cards`() {
        val now = FakeNow.Base()
        val day = 24 * 3600 * 1000
        val sevenDays = 7L * day
        val threeDays = 3L * day
        now.addTime(sevenDays)
        val cacheDataSource = FakeCacheDataSource(
            listOf(
                CardCache(id = 0L, countStartTime = 0L, text = "x"),
                CardCache(id = threeDays, countStartTime = threeDays, text = "three"),
                CardCache(id = sevenDays, countStartTime = sevenDays, text = "y")
            )
        )
        val repository = NewRepository(cacheDataSource, now)

        val actual = repository.cards()
        val expected = listOf(
            Card.NonZeroDays(7, "x", 0L),
            Card.NonZeroDays(4, "three", threeDays),
            Card.ZeroDays("y", sevenDays)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `test new card`() {
        val now = FakeNow.Base()
        val day = 24 * 3600 * 1000
        val sevenDays = 7L * day
        now.addTime(sevenDays)
        val cacheDataSource = FakeCacheDataSource(
            listOf(CardCache(id = 0L, countStartTime = 0L, text = "x"))
        )
        val repository = NewRepository(cacheDataSource, now)

        repository.newCard(text = "new habit")

        val actual = repository.cards()
        val expected = listOf(
            Card.NonZeroDays(7, "x", 0L),
            Card.ZeroDays("new habit", sevenDays)
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `test update card`() {
        val now = FakeNow.Base()
        val cacheDataSource = FakeCacheDataSource(
            listOf(CardCache(id = 0L, countStartTime = 0L, text = "x"))
        )
        val repository = NewRepository(cacheDataSource, now)

        repository.updateCard(0L, "new habit")

        val actual = repository.cards()
        val expected = listOf(Card.ZeroDays("new habit", 0L))

        assertEquals(expected, actual)
    }

    @Test
    fun `test delete card`() {
        val now = FakeNow.Base()
        val cacheDataSource = FakeCacheDataSource(
            listOf(
                CardCache(id = 10L, countStartTime = 10L, text = "x"),
                CardCache(id = 0L, countStartTime = 0L, text = "y")
            )
        )
        val repository = NewRepository(cacheDataSource, now)

        repository.deleteCard(10L)

        val actual = repository.cards()
        val expected = listOf(Card.ZeroDays("y", 0L))
        assertEquals(expected, actual)
    }

    @Test
    fun `test reset card`() {
        val now = FakeNow.Base()
        val day = 24 * 3600 * 1000
        val sevenDays = 7L * day
        now.addTime(sevenDays)
        val cacheDataSource = FakeCacheDataSource(
            listOf(CardCache(id = 0L, countStartTime = 0L, text = "x"))
        )
        val repository = NewRepository(cacheDataSource, now)

        var actual = repository.cards()
        var expected: List<Card> = listOf(Card.NonZeroDays(7, "x", 0L))
        assertEquals(expected, actual)
        repository.resetCard(0L)

        actual = repository.cards()
        expected = listOf(Card.ZeroDays("x", 0L))
        assertEquals(expected, actual)

        assertEquals(
            CardCache(id = 0L, countStartTime = sevenDays, text = "x"),
            cacheDataSource.read()[0]
        )
    }
}

private class FakeCacheDataSource(list: List<CardCache>) : NewCacheDataSource {

    private var cards: MutableList<CardCache> = ArrayList()

    init {
        cards.addAll(list)
    }

    override fun read(): MutableList<CardCache> {
        return cards
    }

    override fun save(list: MutableList<CardCache>) {
        cards = list
    }
}