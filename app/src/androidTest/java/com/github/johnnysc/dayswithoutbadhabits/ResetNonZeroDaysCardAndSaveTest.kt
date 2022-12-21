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
class ResetNonZeroDaysCardAndSaveTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(sharedPref, Gson())
            .save(
                mutableListOf(
                    CardCache(
                        1L,
                        now.time() - 8 * 24 * 3600 * 1000,
                        "days without alcohol"
                    ),
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val nonZeroDaysCard = nonZeroDaysCard(0)
        val addCard = addCard(1)
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("8")
        nonZeroDaysCard.titleView().check("days without alcohol")
        addCard.cardView().checkDisplayed()

        nonZeroDaysCard.editButton().click()

        nonZeroDaysCard.cardView().checkDoesNotExist()
        val nonZeroDaysEditCard = nonZeroDaysEditCard(0)
        nonZeroDaysEditCard.cardView().checkDisplayed()
        nonZeroDaysEditCard.daysView().check("8")
        nonZeroDaysEditCard.inputView().check("days without alcohol")
        nonZeroDaysEditCard.saveButton().checkDisplayed()
        addCard.cardView().checkDisplayed()

        nonZeroDaysEditCard.resetButton().click()
        nonZeroDaysEditCard.saveButton().checkNotDisplayed()
        nonZeroDaysEditCard.deleteButton().checkNotDisplayed()
        nonZeroDaysEditCard.cancelButton().click()
        nonZeroDaysEditCard.saveButton().checkDisplayed()
        nonZeroDaysEditCard.deleteButton().checkDisplayed()
        nonZeroDaysEditCard.resetButton().click()
        nonZeroDaysEditCard.saveButton().checkNotDisplayed()
        nonZeroDaysEditCard.deleteButton().checkNotDisplayed()
        nonZeroDaysEditCard.resetButton().click()

        nonZeroDaysEditCard.cardView().checkDoesNotExist()
        val zeroDaysCard = zeroDaysCard(0)
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without alcohol")
        addCard.cardView().checkDisplayed()
    }
}