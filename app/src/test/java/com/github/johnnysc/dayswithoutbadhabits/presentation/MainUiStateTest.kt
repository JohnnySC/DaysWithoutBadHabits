package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.views.AbstractCardView
import com.github.johnnysc.dayswithoutbadhabits.presentation.views.PositionCallback
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 24.12.2022
 */
class MainUiStateTest {

    @Test
    fun `test remove`() {
        val remove = MainUiState.Remove(5)
        val layout = FakeContainer()
        val makeUi = FakeMakeUi()
        val actions = CardActions.Empty()
        remove.apply(layout, makeUi, actions)

        assertEquals(5, layout.removeList[0])
        assertEquals(1, layout.removeList.size)
    }

    @Test
    fun `test replace`() {
        val replace = MainUiState.Replace(3, Card.Add)
        val layout = FakeContainer()
        val makeUi = FakeMakeUi()
        val actions = CardActions.Empty()
        replace.apply(layout, makeUi, actions)

        assertEquals(3, layout.replaceIndexList[0].first)
        assertEquals(FakeCardUi(Card.Add), layout.replaceIndexList[0].second)
        assertEquals(1, layout.replaceIndexList.size)
    }

    @Test
    fun `test add`() {
        val add = MainUiState.Add(Card.Make)
        val layout = FakeContainer()
        val makeUi = FakeMakeUi()
        val actions = CardActions.Empty()
        add.apply(layout, makeUi, actions)

        assertEquals(FakeCardUi(Card.Make), layout.addList[0])
        assertEquals(1, layout.addList.size)
    }

    @Test
    fun `test add all`() {
        val cards = listOf(Card.Add, Card.Make, Card.ZeroDays("abc", 123L))
        val addAll = MainUiState.AddAll(cards)
        val layout = FakeContainer()
        val makeUi = FakeMakeUi()
        val actions = CardActions.Empty()
        addAll.apply(layout, makeUi, actions)

        assertEquals(FakeCardUi(Card.Add), layout.addList[0])
        assertEquals(FakeCardUi(Card.Make), layout.addList[1])
        assertEquals(FakeCardUi(Card.ZeroDays("abc", 123L)), layout.addList[2])
        assertEquals(3, layout.addList.size)
    }
}

private class FakeMakeUi : MakeUi<FakeCardUi> {
    override fun addCard(card: Card.Add): FakeCardUi = FakeCardUi(card)

    override fun makeCard(card: Card.Make): FakeCardUi = FakeCardUi(card)

    override fun zeroDays(text: String, card: Card.ZeroDays): FakeCardUi = FakeCardUi(card)

    override fun editableZeroDays(id: Long, text: String, card: Card.ZeroDaysEdit): FakeCardUi =
        FakeCardUi(card)

    override fun nonZeroDays(days: Int, text: String, card: Card.NonZeroDays): FakeCardUi =
        FakeCardUi(card)

    override fun editableNonZeroDays(
        id: Long,
        days: Int,
        text: String,
        card: Card.NonZeroDaysEdit
    ) = FakeCardUi(card)
}

private class FakeContainer : CardsContainer<FakeCardUi> {
    var addWithIndexList = mutableListOf<Pair<Int, FakeCardUi>>()
    override fun add(index: Int, view: FakeCardUi) {
        addWithIndexList.add(Pair(index, view))
    }

    var addList = mutableListOf<FakeCardUi>()
    override fun add(view: FakeCardUi) {
        addList.add(view)
    }

    var replaceIndexList = mutableListOf<Pair<Int, FakeCardUi>>()
    override fun replace(index: Int, view: FakeCardUi) {
        replaceIndexList.add(Pair(index, view))
    }

    var removeList = mutableListOf<Int>()
    override fun remove(index: Int) {
        removeList.add(index)
    }

    override fun position(cardUi: AbstractCardView) = Int.MIN_VALUE
    override fun moveUp(position: Int) = Unit

    override fun moveDown(position: Int) = Unit
}

private data class FakeCardUi(val card: Card) : CardUi {

    var initCalledCount = 0
    override fun init(positionCallback: PositionCallback, actions: CardActions) {
        initCalledCount++
    }

    override fun clear() = Unit

    override fun canBeMoved() = false
    override fun hideCanBeMoved() = Unit
    override fun showCanBeMovedDown() = Unit
    override fun showCanBeMovedUp() = Unit
}