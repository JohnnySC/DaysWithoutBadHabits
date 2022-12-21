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
class EditZeroDaysCardAndSaveTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(sharedPref, Gson())
            .save(
                mutableListOf(
                    CardCache(1L, now.time(), "days without alcohol"),
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val zeroDaysCard = zeroDaysCard(0)
        val addCard = addCard(1)
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without alcohol")
        addCard.cardView().checkDisplayed()

        zeroDaysCard.editButton().click()

        zeroDaysCard.cardView().checkDoesNotExist()
        val zeroDaysEditCard = zeroDaysEditCard(0)
        zeroDaysEditCard.cardView().checkDisplayed()
        zeroDaysEditCard.inputView().check("days without alcohol")
        zeroDaysEditCard.saveButton().checkDisplayed()
        addCard.cardView().checkDisplayed()

        zeroDaysEditCard.inputView().type("new text")
        zeroDaysEditCard.saveButton().click()

        zeroDaysEditCard.cardView().checkDoesNotExist()
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("new text")
        addCard.cardView().checkDisplayed()
    }
}