package com.github.johnnysc.dayswithoutbadhabits

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers

/**
 * @author Asatryan on 15.12.2022
 */
abstract class AbstractPage

abstract class AbstractCard(private val cardId: Int, protected val position: Int) {
    fun cardView(): ViewInteraction = cardId.viewAt(position)

    protected fun Int.viewAt(position: Int): ViewInteraction =
        Espresso.onView(CardsLayoutMatcher(R.id.cardsLayout).atPosition(position, this))
}