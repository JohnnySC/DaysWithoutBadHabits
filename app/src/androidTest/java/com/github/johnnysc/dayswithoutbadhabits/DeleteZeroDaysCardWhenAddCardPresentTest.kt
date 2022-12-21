package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.CacheDataSource
import com.github.johnnysc.dayswithoutbadhabits.data.CardCache
import com.github.johnnysc.dayswithoutbadhabits.data.Now
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class DeleteZeroDaysCardWhenAddCardPresentTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(sharedPref, Gson())
            .save(mutableListOf(CardCache(1L, now.time(), "days without alcohol")))
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val zeroDaysCard = zeroDaysCard(0)
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without alcohol")
        var addCard = addCard(1)
        addCard.cardView().checkDisplayed()

        zeroDaysCard.editButton().click()

        addCard.cardView().checkDisplayed()
        zeroDaysCard.cardView().checkDoesNotExist()
        val zeroDaysEditCard = zeroDaysEditCard(0)
        zeroDaysEditCard.cardView().checkDisplayed()
        zeroDaysEditCard.inputView().check("days without alcohol")
        zeroDaysEditCard.saveButton().checkDisplayed()

        zeroDaysEditCard.deleteButton().click()
        zeroDaysEditCard.saveButton().checkNotDisplayed()

        zeroDaysEditCard.cancelButton().click()
        zeroDaysEditCard.saveButton().checkDisplayed()

        zeroDaysEditCard.deleteButton().click()
        zeroDaysEditCard.saveButton().checkNotDisplayed()

        zeroDaysEditCard.deleteButton().click()

        zeroDaysEditCard.cardView().checkDoesNotExist()
        zeroDaysCard.cardView().checkDoesNotExist()
        addCard = addCard(0)
        addCard.cardView().checkDisplayed()
    }
}