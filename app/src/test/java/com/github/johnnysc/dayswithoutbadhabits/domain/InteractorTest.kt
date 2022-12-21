package com.github.johnnysc.dayswithoutbadhabits.domain

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 18.12.2022
 */
class InteractorTest {

    @Test
    fun `test initial less max count`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        val actual = interactor.cards()
        val expected = listOf(Card.ZeroDays("a", 1L), Card.Add)
        assertEquals(expected, actual)
    }

    @Test
    fun `test initial equals max count`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L), Card.ZeroDays("b", 2L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        val actual = interactor.cards()
        val expected = listOf(Card.ZeroDays("a", 1L), Card.ZeroDays("b", 2L))
        assertEquals(expected, actual)
    }

    @Test
    fun `test can add new card`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        val actual = interactor.canAddNewCard()
        val expected = true
        assertEquals(expected, actual)
    }

    @Test
    fun `test cannot add new card`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L), Card.ZeroDays("b", 2L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        val actual = interactor.canAddNewCard()
        val expected = false
        assertEquals(expected, actual)
    }

    @Test
    fun `test new card`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        val actual = interactor.newCard("xyz")
        val expected = Card.ZeroDays("xyz", 7L)
        assertEquals(expected, actual)

        val cardsActual = interactor.cards()
        val cardsExpected = listOf(Card.ZeroDays("a", 1L), Card.ZeroDays("xyz", 7L))
        assertEquals(cardsExpected, cardsActual)
    }

    @Test
    fun `test delete card`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L), Card.ZeroDays("b", 2L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        interactor.deleteCard(2L)

        val actual = interactor.cards()
        val expected = listOf(Card.ZeroDays("a", 1L), Card.Add)
        assertEquals(expected, actual)
    }

    @Test
    fun `test update card`() {
        val repository = FakeRepository(listOf(Card.ZeroDays("a", 1L), Card.ZeroDays("b", 2L)))
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        interactor.updateCard(1L, "c")

        val actual = interactor.cards()
        val expected = listOf(Card.ZeroDays("c", 1L), Card.ZeroDays("b", 2L))
        assertEquals(expected, actual)
    }

    @Test
    fun `test reset card`() {
        val repository = FakeRepository(
            listOf(
                Card.NonZeroDays(12, "a", 1L),
                Card.NonZeroDays(13, "b", 2L)
            )
        )
        val interactor = MainInteractor.Base(repository, maxItemsCount = 2)

        interactor.resetCard(1L)

        val actual = interactor.cards()
        val expected = listOf(
            Card.ZeroDays("a", 1L),
            Card.NonZeroDays(13, "b", 2L)
        )
        assertEquals(expected, actual)
    }

}

private class FakeRepository(items: List<Card>) : Repository {
    private val list: MutableList<Card> = ArrayList()

    init {
        list.addAll(items)
    }

    override fun cards(): List<Card> {
        return list
    }

    override fun newCard(text: String): Card {
        val zeroDays = Card.ZeroDays(text, 7L)
        list.add(zeroDays)
        return zeroDays
    }

    override fun deleteCard(id: Long) {
        val item = list.find { it.map(Card.Mapper.Same(id)) }
        list.remove(item)
    }

    override fun updateCard(id: Long, text: String) {
        val item = list.find { it.map(Card.Mapper.Same(id)) }
        val index = list.indexOf(item)
        val newItem = item!!.map(Card.Mapper.Duplicate(text))
        list[index] = newItem
    }

    override fun resetCard(id: Long) {
        val item = list.find { it.map(Card.Mapper.Same(id)) }
        val index = list.indexOf(item)
        val newItem = item!!.map(Card.Mapper.ResetDays())
        list[index] = newItem
    }
}