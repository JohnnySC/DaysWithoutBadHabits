package com.github.johnnysc.dayswithoutbadhabits.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.domain.NewMainInteractor
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Asatryan on 17.12.2022
 */
class NewViewModelTest {

    //region add and make
    @Test
    fun `first start`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.init(isFirstRun = false)
        assertEquals(1, communication.list.size)
    }

    @Test
    fun `add first card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)
    }

    @Test
    fun `cancel make card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        viewModel.cancelMakeCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Add), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `save first card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true
        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals("days without smoking", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(true, interactor.canAddNewCardList[0])
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "days without smoking", id = 4L)
            ),
            communication.list[2]
        )
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.list.size)
    }

    @Test
    fun `save only one card`() {
        val communication = FakeCommunication()
        val interactor = FakeInteractor(listOf(Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(NewUiState.AddAll(listOf(Card.Add)), communication.list[0])
        assertEquals(1, communication.list.size)

        viewModel.addCard(position = 0)
        assertEquals(NewUiState.Replace(position = 0, card = Card.Make), communication.list[1])
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false
        viewModel.saveNewCard(text = "days without smoking", position = 0)
        assertEquals("days without smoking", interactor.saveNewCardList[0])
        assertEquals(1, interactor.saveNewCardList.size)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(false, interactor.canAddNewCardList[0])
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "days without smoking", id = 4L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }
    //endregion

    //region edit zero days
    @Test
    fun `test edit zero days card and cancel`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(listOf(Card.ZeroDays(text = "days without smoking", id = 1L), Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.ZeroDays(text = "days without smoking", id = 1L),
                    Card.Add
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editZeroDaysCard(
            position = 0,
            card = Card.ZeroDays(text = "days without smoking", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDaysEdit(text = "days without smoking", id = 1L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.cancelEditZeroDaysCard(
            position = 0,
            card = Card.ZeroDaysEdit(text = "days without smoking", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDays(text = "days without smoking", id = 1L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test delete zero days card when add card present`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(listOf(Card.ZeroDays(text = "days without smoking", id = 1L), Card.Add))
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.ZeroDays(text = "days without smoking", id = 1L),
                    Card.Add
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editZeroDaysCard(
            position = 0,
            card = Card.ZeroDays(text = "days without smoking", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.ZeroDaysEdit(text = "days without smoking", id = 1L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true
        viewModel.deleteCard(position = 0, id = 1L)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(true, interactor.canAddNewCardList[0])
        assertEquals(1, interactor.deleteCardList.size)
        assertEquals(1L, interactor.deleteCardList[0])
        assertEquals(NewUiState.Remove(position = 0), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test delete zero days card when add card not present`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.ZeroDays(text = "days without smoking", id = 1L),
                    Card.ZeroDays(text = "days without alcohol", id = 2L),
                )
            )
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)

        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.ZeroDays(text = "days without smoking", id = 1L),
                    Card.ZeroDays(text = "days without alcohol", id = 2L),
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editZeroDaysCard(
            position = 1,
            card = Card.ZeroDays(text = "days without alcohol", id = 2L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDaysEdit(text = "days without alcohol", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false
        viewModel.deleteCard(position = 1, id = 2L)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(false, interactor.canAddNewCardList[0])
        assertEquals(1, interactor.deleteCardList.size)
        assertEquals(2L, interactor.deleteCardList[0])
        assertEquals(NewUiState.Remove(position = 1), communication.list[2])
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.list.size)
    }

    @Test
    fun `test edit zero days card and save`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.ZeroDays(text = "days without smoking", id = 1L),
                    Card.ZeroDays(text = "days without alcohol", id = 2L),
                )
            )
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.ZeroDays(text = "days without smoking", id = 1L),
                    Card.ZeroDays(text = "days without alcohol", id = 2L),
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editZeroDaysCard(
            position = 1,
            card = Card.ZeroDays(text = "days without alcohol", id = 2L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDaysEdit(text = "days without alcohol", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.saveEditedZeroDaysCard(text = "days without vodka", position = 1, id = 2L)
        assertEquals(1, interactor.updateCardList.size)
        assertEquals(2L, interactor.updateCardList[0])
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDays(text = "days without vodka", id = 2L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }
    //endregion

    //region edit non-zero days
    @Test
    fun `test edit non-zero days card and cancel`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.NonZeroDays(
                        days = 12,
                        text = "days without smoking",
                        id = 1L
                    ), Card.Add
                )
            )
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.Add
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editNonZeroDaysCard(
            position = 0,
            card = Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.NonZeroDaysEdit(days = 12, text = "days without smoking", id = 1L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.cancelEditNonZeroDaysCard(
            position = 0,
            card = Card.NonZeroDaysEdit(days = 12, text = "days without smoking", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test delete non-zero days card when add card present`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.NonZeroDays(
                        days = 12,
                        text = "days without smoking",
                        id = 1L
                    ),
                    Card.Add
                )
            )
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.Add
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editNonZeroDaysCard(
            position = 0,
            card = Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 0,
                card = Card.NonZeroDaysEdit(days = 12, text = "days without smoking", id = 1L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = true
        viewModel.deleteCard(position = 0, id = 1L)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(true, interactor.canAddNewCardList[0])
        assertEquals(1, interactor.deleteCardList.size)
        assertEquals(1L, interactor.deleteCardList[0])
        assertEquals(NewUiState.Remove(position = 0), communication.list[2])
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test delete non-zero days card when add card not present`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L),
                )
            )
        val viewModel = NewViewModel(communication, interactor)
        viewModel.init(isFirstRun = true)

        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L),
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editNonZeroDaysCard(
            position = 1,
            card = Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.NonZeroDaysEdit(days = 12, text = "days without alcohol", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        interactor.canAddNewCard = false
        viewModel.deleteCard(position = 1, id = 2L)
        assertEquals(1, interactor.canAddNewCardList.size)
        assertEquals(false, interactor.canAddNewCardList[0])
        assertEquals(1, interactor.deleteCardList.size)
        assertEquals(2L, interactor.deleteCardList[0])
        assertEquals(NewUiState.Remove(position = 1), communication.list[2])
        assertEquals(NewUiState.Add(Card.Add), communication.list[3])
        assertEquals(4, communication.list.size)
    }

    @Test
    fun `test edit non-zero days card and save`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L),
                )
            )
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L),
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editNonZeroDaysCard(
            position = 1,
            card = Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.NonZeroDaysEdit(days = 12, text = "days without alcohol", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.saveEditedNonZeroDaysCard(
            days = 12,
            text = "days without vodka",
            position = 1,
            id = 2L
        )
        assertEquals(1, interactor.updateCardList.size)
        assertEquals(2L, interactor.updateCardList[0])
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.NonZeroDays(days = 12, text = "days without vodka", id = 2L)
            ),
            communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }

    @Test
    fun `test reset non-zero days card`() {
        val communication = FakeCommunication()
        val interactor =
            FakeInteractor(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L),
                )
            )
        val viewModel = NewViewModel(communication, interactor)

        viewModel.init(isFirstRun = true)
        assertEquals(
            NewUiState.AddAll(
                listOf(
                    Card.NonZeroDays(days = 12, text = "days without smoking", id = 1L),
                    Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L),
                )
            ),
            communication.list[0]
        )
        assertEquals(1, communication.list.size)

        viewModel.editNonZeroDaysCard(
            position = 1,
            card = Card.NonZeroDays(days = 12, text = "days without alcohol", id = 2L)
        )
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.NonZeroDaysEdit(days = 12, text = "days without alcohol", id = 2L)
            ),
            communication.list[1]
        )
        assertEquals(2, communication.list.size)

        viewModel.resetNonZeroDaysCard(
            position = 1,
            card = Card.NonZeroDaysEdit(days = 12, text = "days without alcohol", id = 2L)
        )
        assertEquals(2L, interactor.resetCardList[0])
        assertEquals(1, interactor.resetCardList.size)
        assertEquals(
            NewUiState.Replace(
                position = 1,
                card = Card.ZeroDays(text = "days without alcohol", id = 2L)
            ), communication.list[2]
        )
        assertEquals(3, communication.list.size)
    }
    //endregion
}

private class FakeInteractor(private val cards: List<Card>) : NewMainInteractor {

    var canAddNewCard: Boolean = true
    val canAddNewCardList = mutableListOf<Boolean>()
    var saveNewCardList = mutableListOf<String>()
    var deleteCardList = mutableListOf<Long>()
    var updateCardList = mutableListOf<Long>()
    var resetCardList = mutableListOf<Long>()

    override fun cards(): List<Card> {
        return cards
    }

    override fun canAddNewCard(): Boolean {
        canAddNewCardList.add(canAddNewCard)
        return canAddNewCard
    }

    override fun newCard(text: String): Card {
        saveNewCardList.add(text)
        return Card.ZeroDays(text = text, id = 4L)
    }

    override fun deleteCard(id: Long) {
        deleteCardList.add(id)
    }

    override fun updateCard(id: Long, newText: String) {
        updateCardList.add(id)
    }

    override fun resetCard(id: Long) {
        resetCardList.add(id)
    }
}

private class FakeCommunication : NewMainCommunication.Mutable {

    val list = mutableListOf<NewUiState>()

    override fun put(value: NewUiState) {
        list.add(value)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NewUiState>) = Unit
}
