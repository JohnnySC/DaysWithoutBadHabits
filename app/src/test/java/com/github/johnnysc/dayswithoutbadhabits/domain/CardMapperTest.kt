package com.github.johnnysc.dayswithoutbadhabits.domain

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 18.12.2022
 */
class CardMapperTest {

    @Test
    fun `test reset`() {
        val card = Card.NonZeroDays(12, "x", 1L)
        val actual = card.map(Card.Mapper.ResetDays())
        val expected = Card.ZeroDays("x", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test same card`() {
        val card = Card.NonZeroDays(12, "x", 1L)
        val actual = card.map(Card.Mapper.Same(1L))
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun `test not same card`() {
        val card = Card.NonZeroDays(12, "x", 1L)
        val actual = card.map(Card.Mapper.Same(2L))
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `test duplicate zero days`() {
        val card = Card.ZeroDays("x", 1L)
        val actual = card.map(Card.Mapper.Duplicate("y"))
        val expected = Card.ZeroDays("y", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test duplicate zero days edit`() {
        val card = Card.ZeroDaysEdit("x", 1L)
        val actual = card.map(Card.Mapper.Duplicate("y"))
        val expected = Card.ZeroDaysEdit("y", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test duplicate non-zero days`() {
        val card = Card.NonZeroDays(12, "x", 1L)
        val actual = card.map(Card.Mapper.Duplicate("y"))
        val expected = Card.NonZeroDays(12, "y", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test duplicate non-zero days edit`() {
        val card = Card.NonZeroDaysEdit(12, "x", 1L)
        val actual = card.map(Card.Mapper.Duplicate("y"))
        val expected = Card.NonZeroDaysEdit(12, "y", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test duplicate add`() {
        val card = Card.Add
        val actual = card.map(Card.Mapper.Duplicate("y"))
        val expected = Card.Add
        assertEquals(expected, actual)
    }

    @Test
    fun `test duplicate make`() {
        val card = Card.Make
        val actual = card.map(Card.Mapper.Duplicate("y"))
        val expected = Card.Make
        assertEquals(expected, actual)
    }

    @Test
    fun `test change editable zero days`() {
        val card = Card.ZeroDays("x", 1L)
        val actual = card.map(Card.Mapper.ChangeEditable())
        val expected = Card.ZeroDaysEdit("x", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test change editable zero days edit`() {
        val card = Card.ZeroDaysEdit("x", 1L)
        val actual = card.map(Card.Mapper.ChangeEditable())
        val expected = Card.ZeroDays("x", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test change editable non-zero days`() {
        val card = Card.NonZeroDays(13, "x", 1L)
        val actual = card.map(Card.Mapper.ChangeEditable())
        val expected = Card.NonZeroDaysEdit(13, "x", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test change editable non-zero days edit`() {
        val card = Card.NonZeroDaysEdit(14, "x", 1L)
        val actual = card.map(Card.Mapper.ChangeEditable())
        val expected = Card.NonZeroDays(14, "x", 1L)
        assertEquals(expected, actual)
    }

    @Test
    fun `test change editable add`() {
        val card = Card.Add
        val actual = card.map(Card.Mapper.ChangeEditable())
        val expected = Card.Add
        assertEquals(expected, actual)
    }

    @Test
    fun `test change editable make`() {
        val card = Card.Make
        val actual = card.map(Card.Mapper.ChangeEditable())
        val expected = Card.Make
        assertEquals(expected, actual)
    }

    @Test
    fun `test reset days`() {
        val card = Card.NonZeroDaysEdit(15, "a", 101L)
        val resetCard = FakeResetCard()
        card.map(Card.Mapper.Reset(resetCard))
        assertEquals(101L, resetCard.calledWithId)
    }
}

private class FakeResetCard : ResetCard {
    var calledWithId = -1L

    override fun resetCard(id: Long) {
        calledWithId = id
    }
}