package com.github.johnnysc.dayswithoutbadhabits.domain

import com.github.johnnysc.dayswithoutbadhabits.presentation.MakeUi
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 24.12.2022
 */
class CardMakeUiTest {

    @Test
    fun `test make ui add`() {
        val makeUi = FakeMakeUi()
        val actual = Card.Add.make(makeUi)
        val expected = FakeView.Add(Card.Add)
        assertEquals(expected, actual)
    }

    @Test
    fun `test make ui make card`() {
        val makeUi = FakeMakeUi()
        val actual = Card.Make.make(makeUi)
        val expected = FakeView.Make(Card.Make)
        assertEquals(expected, actual)
    }

    @Test
    fun `test make ui zero days`() {
        val makeUi = FakeMakeUi()
        val card = Card.ZeroDays("a", 1L)
        val actual = card.make(makeUi)
        val expected = FakeView.ZeroDays("a", card)
        assertEquals(expected, actual)
    }

    @Test
    fun `test make ui zero days edit`() {
        val makeUi = FakeMakeUi()
        val card = Card.ZeroDaysEdit("b", 2L)
        val actual = card.make(makeUi)
        val expected = FakeView.ZeroDaysEdit(2L, "b", card)
        assertEquals(expected, actual)
    }

    @Test
    fun `test make ui non-zero days`() {
        val makeUi = FakeMakeUi()
        val card = Card.NonZeroDays(4, "c", 3L)
        val actual = card.make(makeUi)
        val expected = FakeView.NonZeroDays(4, "c", card)
        assertEquals(expected, actual)
    }

    @Test
    fun `test make ui non-zero days edit`() {
        val makeUi = FakeMakeUi()
        val card = Card.NonZeroDaysEdit(4, "c", 3L)
        val actual = card.make(makeUi)
        val expected = FakeView.NonZeroDaysEdit(3L, 4, "c", card)
        assertEquals(expected, actual)
    }

    private class FakeMakeUi : MakeUi<FakeView> {

        override fun addCard(card: Card.Add) = FakeView.Add(card)

        override fun makeCard(card: Card.Make) = FakeView.Make(card)

        override fun zeroDays(text: String, card: Card.ZeroDays) =
            FakeView.ZeroDays(text, card)

        override fun editableZeroDays(id: Long, text: String, card: Card.ZeroDaysEdit) =
            FakeView.ZeroDaysEdit(id, text, card)

        override fun nonZeroDays(days: Int, text: String, card: Card.NonZeroDays) =
            FakeView.NonZeroDays(days, text, card)

        override fun editableNonZeroDays(
            id: Long,
            days: Int,
            text: String,
            card: Card.NonZeroDaysEdit
        ) = FakeView.NonZeroDaysEdit(id, days, text, card)
    }

    private sealed class FakeView {

        data class Add(val card: Card) : FakeView()
        data class Make(val card: Card) : FakeView()
        data class ZeroDays(val text: String, val card: Card) : FakeView()
        data class ZeroDaysEdit(val id: Long, val text: String, val card: Card) : FakeView()
        data class NonZeroDays(val days: Int, val text: String, val card: Card) : FakeView()
        data class NonZeroDaysEdit(val id: Long, val days: Int, val text: String, val card: Card) :
            FakeView()
    }
}