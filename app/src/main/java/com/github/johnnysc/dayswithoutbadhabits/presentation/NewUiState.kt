package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 17.12.2022
 */
sealed class NewUiState {

    data class AddAll(private val cards: List<Card>) : NewUiState()

    data class Add(private val card: Card) : NewUiState()

    data class Replace(private val position: Int, private val card: Card) : NewUiState()

    data class Remove(private val position: Int) : NewUiState()
}