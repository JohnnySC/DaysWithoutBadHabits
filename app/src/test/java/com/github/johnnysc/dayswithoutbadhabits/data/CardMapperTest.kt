package com.github.johnnysc.dayswithoutbadhabits.data

import com.github.johnnysc.dayswithoutbadhabits.BaseTest
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 18.12.2022
 */
class CardMapperTest : BaseTest() {

    @Test
    fun `test zero days`() {
        val now = FakeNow.Base()
        now.addTime(100L)
        val mapper = CardMapper.Base(now)
        val actual = mapper.map(1L, 100L, "x")
        val expected = Card.ZeroDays("x", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test non-zero days`() {
        val now = FakeNow.Base()
        val day = 24 * 3600 * 1000
        now.addTime(7L * day)
        val mapper = CardMapper.Base(now)

        val actual = mapper.map(12L, 3L * day, "y")
        val expected = Card.NonZeroDays(4, "y", 12L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test same`() {
        val card = CardCache(1L, 12L, "text")
        var actual = card.map(CardMapper.Same(1L))
        var expected = true
        assertEquals(expected, actual)
        actual = card.map(CardMapper.Same(2L))
        expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `test update text`() {
        val card = CardCache(1L, 12L, "first")
        val actual = card.map(CardMapper.UpdateText("new text"))
        val expected = CardCache(1L, 12L, "new text")
        assertEquals(expected, actual)
    }

    @Test
    fun `test update time`() {
        val card = CardCache(1L, 12L, "first")
        val actual = card.map(CardMapper.UpdateTime(54))
        val expected = CardCache(1L, 54, "first")
        assertEquals(expected, actual)
    }
}