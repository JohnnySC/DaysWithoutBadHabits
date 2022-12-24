package com.github.johnnysc.dayswithoutbadhabits.domain

import com.github.johnnysc.dayswithoutbadhabits.presentation.MoveCardActions

/**
 * @author Asatryan on 17.12.2022
 */
interface MainInteractor : CRUDCards, MoveCardActions {

    fun canAddNewCard(): Boolean

    class Base(
        private val repository: Repository,
        private val maxItemsCount: Int
    ) : MainInteractor {

        override fun cards(): List<Card> {
            val cards = repository.cards()
            return if (cards.size < maxItemsCount)
                mutableListOf<Card>().apply {
                    addAll(cards)
                    add(Card.Add)
                }
            else
                cards
        }

        override fun canAddNewCard(): Boolean {
            val cards = repository.cards()
            return cards.size < maxItemsCount
        }

        override fun newCard(text: String): Card {
            return repository.newCard(text)
        }

        override fun deleteCard(id: Long) {
            repository.deleteCard(id)
        }

        override fun updateCard(id: Long, newText: String) {
            repository.updateCard(id, newText)
        }

        override fun resetCard(id: Long) {
            repository.resetCard(id)
        }

        override fun moveCardUp(position: Int) = repository.moveCardUp(position)

        override fun moveCardDown(position: Int) = repository.moveCardDown(position)
    }
}

interface ResetCard {
    fun resetCard(id: Long)
}

interface CRUDCards : ResetCard {
    fun cards(): List<Card>

    fun newCard(text: String): Card

    fun updateCard(id: Long, newText: String)

    fun deleteCard(id: Long)
}