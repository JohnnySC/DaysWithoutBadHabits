package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 17.12.2022
 */
sealed class MainUiState {

    abstract fun <T : CardUi> apply(
        layout: CardsContainer<T>,
        makeUi: MakeUi<T>,
        actions: CardActions
    )

    data class AddAll(private val cards: List<Card>) : MainUiState() {
        override fun <T : CardUi> apply(
            layout: CardsContainer<T>,
            makeUi: MakeUi<T>,
            actions: CardActions
        ) = cards.forEach { card ->
            Add(card).apply(layout, makeUi, actions)
        }
    }

    data class Add(private val card: Card) : MainUiState() {
        override fun <T : CardUi> apply(
            layout: CardsContainer<T>,
            makeUi: MakeUi<T>,
            actions: CardActions
        ) {
            val view = card.make(makeUi)
            view.init(layout, actions)
            layout.add(view)
        }
    }

    data class Replace(private val position: Int, private val card: Card) : MainUiState() {
        override fun <T : CardUi> apply(
            layout: CardsContainer<T>,
            makeUi: MakeUi<T>,
            actions: CardActions
        ) {
            val view = card.make(makeUi)
            view.init(layout, actions)
            layout.replace(position, view)
        }
    }

    data class Remove(private val position: Int) : MainUiState() {
        override fun <T : CardUi> apply(
            layout: CardsContainer<T>,
            makeUi: MakeUi<T>,
            actions: CardActions
        ) = layout.remove(position)
    }
}