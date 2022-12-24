package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.domain.Card

interface CardActions :
    MoveCardActions,
    AddCardActions,
    MakeCardActions,
    ZeroDaysCardActions,
    ZeroDaysEditActions,
    NonZeroDaysEditCardActions,
    NonZeroDaysCardActions {

    class Empty : CardActions {
        override fun moveCardUp(position: Int) = Unit
        override fun moveCardDown(position: Int) = Unit
        override fun addCard(position: Int) = Unit
        override fun cancelMakeCard(position: Int) = Unit
        override fun saveNewCard(text: String, position: Int) = Unit
        override fun editZeroDaysCard(position: Int, card: Card.ZeroDays) = Unit
        override fun cancelEditZeroDaysCard(position: Int, card: Card.ZeroDaysEdit) = Unit
        override fun saveEditedZeroDaysCard(text: String, position: Int, id: Long) = Unit
        override fun deleteCard(position: Int, id: Long) = Unit
        override fun resetNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) = Unit
        override fun saveEditedNonZeroDaysCard(days: Int, text: String, position: Int, id: Long) =
            Unit
        override fun cancelEditNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) = Unit
        override fun editNonZeroDaysCard(position: Int, card: Card.NonZeroDays) = Unit
    }
}