package com.github.johnnysc.dayswithoutbadhabits

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction

/**
 * @author Asatryan on 15.12.2022
 */
abstract class AbstractCard(private val cardId: Int, protected val position: Int) {
    fun cardView(): ViewInteraction = cardId.viewAt(position)

    protected fun Int.viewAt(position: Int): ViewInteraction =
        onView(CardsLayoutMatcher(R.id.cardsLayout).atPosition(position, this))

    protected fun Int.viewAt(position: Int, childPosition: Int): ViewInteraction = onView(
        CardsLayoutMatcher(R.id.cardsLayout).atPositionWithChild(position, this, childPosition)
    )
}