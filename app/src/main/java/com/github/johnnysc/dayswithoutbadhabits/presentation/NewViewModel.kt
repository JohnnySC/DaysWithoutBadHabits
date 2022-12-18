package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.core.Init
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.domain.NewMainInteractor

/**
 * @author Asatryan on 17.12.2022
 */
class NewViewModel(
    private val communication: NewMainCommunication.Mutable,
    private val interactor: NewMainInteractor,
    private val changeEditable: Card.Mapper<Card> = Card.Mapper.ChangeEditable()
) : Init, NewViewModelActions {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun)
            communication.put(NewUiState.AddAll(interactor.cards()))
    }

    override fun addCard(position: Int) =
        communication.put(NewUiState.Replace(position, Card.Make))

    override fun cancelMakeCard(position: Int) =
        communication.put(NewUiState.Replace(position, Card.Add))

    override fun saveNewCard(text: String, position: Int) {
        val card = interactor.newCard(text)
        val canAddNewCard = interactor.canAddNewCard()
        communication.put(NewUiState.Replace(position, card))
        if (canAddNewCard)
            communication.put(NewUiState.Add(Card.Add))
    }

    override fun editZeroDaysCard(position: Int, card: Card.ZeroDays) {
        communication.put(NewUiState.Replace(position, card.map(changeEditable)))
    }

    override fun cancelEditZeroDaysCard(position: Int, card: Card.ZeroDaysEdit) =
        communication.put(NewUiState.Replace(position, card.map(changeEditable)))

    override fun deleteCard(position: Int, id: Long) {
        val canAddNewCard = interactor.canAddNewCard()
        interactor.deleteCard(id)
        communication.put(NewUiState.Remove(position))
        if (!canAddNewCard)
            communication.put(NewUiState.Add(Card.Add))
    }

    override fun saveEditedZeroDaysCard(text: String, position: Int, id: Long) {
        interactor.updateCard(id, text)
        communication.put(NewUiState.Replace(position, Card.ZeroDays(text, id)))
    }

    override fun editNonZeroDaysCard(position: Int, card: Card.NonZeroDays) =
        communication.put(NewUiState.Replace(position, card.map(changeEditable)))

    override fun cancelEditNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) =
        communication.put(NewUiState.Replace(position, card.map(changeEditable)))

    override fun saveEditedNonZeroDaysCard(days: Int, text: String, position: Int, id: Long) {
        interactor.updateCard(id, text)
        communication.put(NewUiState.Replace(position, Card.NonZeroDays(days, text, id)))
    }

    override fun resetNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit) {
        card.map(Card.Mapper.Reset(interactor))
        communication.put(NewUiState.Replace(position, card.map(Card.Mapper.ResetDays())))
    }
}

/**
 * todo refactor ISP
 */
interface NewViewModelActions : AddCard, CancelMakeCard, SaveNewCard, EditZeroDaysCard,
    CancelEditZeroDaysCard, DeleteCard, SaveEditedZeroDaysCard, EditNonZeroDaysCard,
    CancelEditNonZeroDaysCard, SaveEditedNonZeroDaysCard, ResetNonZeroDaysCard

interface AddCard {
    fun addCard(position: Int)
}

interface CancelMakeCard {
    fun cancelMakeCard(position: Int)
}

interface SaveNewCard {
    fun saveNewCard(text: String, position: Int)
}

interface EditZeroDaysCard {
    fun editZeroDaysCard(position: Int, card: Card.ZeroDays)
}

interface CancelEditZeroDaysCard {
    fun cancelEditZeroDaysCard(position: Int, card: Card.ZeroDaysEdit)
}

interface DeleteCard {
    fun deleteCard(position: Int, id: Long)
}

interface SaveEditedZeroDaysCard {
    fun saveEditedZeroDaysCard(text: String, position: Int, id: Long)
}

interface EditNonZeroDaysCard {
    fun editNonZeroDaysCard(position: Int, card: Card.NonZeroDays)
}

interface CancelEditNonZeroDaysCard {
    fun cancelEditNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit)
}

interface SaveEditedNonZeroDaysCard {
    fun saveEditedNonZeroDaysCard(days: Int, text: String, position: Int, id: Long)
}

interface ResetNonZeroDaysCard {
    fun resetNonZeroDaysCard(position: Int, card: Card.NonZeroDaysEdit)
}