package com.github.johnnysc.dayswithoutbadhabits.presentation

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 24.12.2022
 */
class ShowCardsNavigationButtonsTest {

    @Test
    fun test_one_item() {
        val item = FakeItem(true)
        ShowCardsNavigationButtons.Base(listOf(item)).show()
        assertEquals(1, item.hideCalledCount)
    }

    @Test
    fun test_zero_items() {
        val item = FakeItem(false)
        ShowCardsNavigationButtons.Base(listOf(item)).show()
        assertEquals(0, item.hideCalledCount)
        assertEquals(0, item.showUpCalledCount)
        assertEquals(0, item.showDownCalledCount)
        assertEquals(0, item.showUpAndDownCalledCount)
    }

    @Test
    fun test_3_items() {
        val itemOne = FakeItem(true)
        val itemTwo = FakeItem(true)
        val itemThree = FakeItem(true)
        ShowCardsNavigationButtons.Base(listOf(itemOne, itemTwo, itemThree)).show()
        assertEquals(1, itemOne.hideCalledCount)
        assertEquals(1, itemTwo.hideCalledCount)
        assertEquals(1, itemThree.hideCalledCount)

        assertEquals(1, itemOne.showDownCalledCount)
        assertEquals(1, itemTwo.showUpAndDownCalledCount)
        assertEquals(1, itemThree.showUpCalledCount)
    }
}

private class FakeItem(private val canBeMoved: Boolean) : CanBeMoved {

    override fun canBeMoved(): Boolean = canBeMoved

    var hideCalledCount = 0
    var showUpCalledCount = 0
    var showDownCalledCount = 0
    var showUpAndDownCalledCount = 0

    override fun hideCanBeMoved() {
        hideCalledCount++
    }

    override fun showCanBeMovedDown() {
        showDownCalledCount++
    }

    override fun showCanBeMovedUp() {
        showUpCalledCount++
    }

    override fun showCanBeMovedUpAndDown() {
        showUpAndDownCalledCount++
    }
}