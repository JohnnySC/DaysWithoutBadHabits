package com.github.johnnysc.dayswithoutbadhabits.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.johnnysc.dayswithoutbadhabits.core.Communication
import com.github.johnnysc.dayswithoutbadhabits.core.Init
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.domain.MainInteractor

/**
 * @author Asatryan on 17.12.2022
 */
class MainViewModel(
    private val communication: MainCommunication.Mutable,
    private val interactor: MainInteractor,
    private val changeEditable: Card.Mapper<Card> = Card.Mapper.ChangeEditable(),
) : Init, Communication.Observe<MainUiState>, CardActions {

    override fun observe(owner: LifecycleOwner, observer: Observer<MainUiState>) =
        communication.observe(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            communication.put(MainUiState.AddAll(interactor.cards()))
    }

    override fun moveCardUp(position: Int) = interactor.moveCardUp(position)

    override fun moveCardDown(position: Int) = interactor.moveCardDown(position)

    override fun addCard(position: Int) =
        communication.put(MainUiState.Replace(position, Card.Make))

    override fun cancelMakeCard(position: Int) =
        communication.put(MainUiState.Replace(position, Card.Add))

    override fun saveNewCard(text: String, position: Int) {
        val card = interactor.newCard(text)
        val canAddNewCard = interactor.canAddNewCard()
        communication.put(MainUiState.Replace(position, card))
        if (canAddNewCard)
            communication.put(MainUiState.Add(Card.Add))
    }

    override fun editZeroDaysCard(position: Int, card: Card.ZeroDays) {
        communication.put(MainUiState.Replace(position, card.map(changeEditable)))
    }

    override fun cancelEditZeroDaysCard(position: Int, card: Card.ZeroDaysEdit) =
        communication.put(MainUiState.Replace(position, card.map(changeEditable)))

    override fun deleteCard(position: Int, id: Long) {
        val canAddNewCard = interactor.canAddNewCard()
        interactor.deleteCard(id)
        communication.put(MainUiState.Remove(position))
        if (!canAddNewCard)
            communication.put(MainUiState.Add(Card.Add))
    }

    override fun saveEditedZeroDaysCard(text: String, position: Int, id: Long) {
        interactor.updateCard(id, text)
        communication.put(MainUiState.Replace(position, Card.ZeroDays(text, id)))
    }

    override fun editNonZeroDaysCard(position: Int, card: Card.NonZeroDays) =
        communication.put(MainUiState.Replace(position, card.map(changeEditable)))

    override fun cancelEditNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) =
        communication.put(MainUiState.Replace(position, card.map(changeEditable)))

    override fun saveEditedNonZeroDaysCard(days: Int, text: String, position: Int, id: Long) {
        interactor.updateCard(id, text)
        communication.put(MainUiState.Replace(position, Card.NonZeroDays(days, text, id)))
    }

    override fun resetNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) {
        card.map(Card.Mapper.Reset(interactor))
        communication.put(MainUiState.Replace(position, card.map(Card.Mapper.ResetDays())))
    }
}