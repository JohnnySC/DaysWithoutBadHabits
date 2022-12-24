package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.CacheDataSource
import com.github.johnnysc.dayswithoutbadhabits.data.CardCache
import com.github.johnnysc.dayswithoutbadhabits.data.Now
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 24.12.2022
 */
class MoveOneZeroDaysCardTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(sharedPref, Gson())
            .save(
                mutableListOf(
                    CardCache(
                        1L,
                        now.time() - 3600 * 1000,
                        "days without alcohol"
                    ),
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val card = zeroDaysCard(0)
        val addButton = addCard(1)
        addButton.cardView().checkDisplayed()

        card.editButton().click()

        card.cardView().checkDoesNotExist()
        val edited = zeroDaysEditCard(0)
        edited.cardView().checkDisplayed()

        edited.upButton().checkNotDisplayed()
        edited.downButton().checkNotDisplayed()

        addButton.cardView().click()
        val makeCard = makeCard(1)
        makeCard.cardView().checkDisplayed()
        makeCard.inputView().type("new card")
        makeCard.saveButton().click()

        makeCard.cardView().checkDoesNotExist()
        val nextZeroDaysCard = zeroDaysCard(1)
        nextZeroDaysCard.cardView().checkDisplayed()

        edited.downButton().checkDisplayed()
        edited.upButton().checkNotDisplayed()

        edited.downButton().click()

        val newFirstCard = zeroDaysCard(0)
        newFirstCard.cardView().checkDisplayed()
        newFirstCard.titleView().check("new card")
        val newSecondCard = zeroDaysEditCard(1)
        newSecondCard.cardView().checkDisplayed()
        newSecondCard.inputView().check("days without alcohol")
        newSecondCard.downButton().checkNotDisplayed()
        newSecondCard.upButton().checkDisplayed()

        newSecondCard.cancelButton().click()
        newSecondCard.cardView().checkDoesNotExist()
        val cancelledCard = zeroDaysCard(1)
        cancelledCard.cardView().checkDisplayed()
        cancelledCard.titleView().check("days without alcohol")
    }
}