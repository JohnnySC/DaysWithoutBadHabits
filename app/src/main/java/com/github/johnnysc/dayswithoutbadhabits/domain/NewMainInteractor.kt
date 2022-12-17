package com.github.johnnysc.dayswithoutbadhabits.domain

/**
 * @author Asatryan on 17.12.2022
 */
interface NewMainInteractor {

    fun cards(): List<Card>

    fun canAddNewCard(): Boolean

    fun newCard(text: String): Card

    fun deleteCard(id: Long)

    fun updateCard(id: Long, newText: String)

    fun resetCard(id: Long)
}