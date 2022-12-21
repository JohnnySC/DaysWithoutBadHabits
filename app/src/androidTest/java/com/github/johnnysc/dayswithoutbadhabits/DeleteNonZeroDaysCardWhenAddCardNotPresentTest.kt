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
class DeleteNonZeroDaysCardWhenAddCardNotPresentTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(sharedPref, Gson())
            .save(
                mutableListOf(
                    CardCache(1L, now.time() - 5 * 24 * 3600 * 1000, "days without alcohol"),
                    CardCache(2L, now.time() - 60000, "days without smoking")
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val nonZeroDaysCard = nonZeroDaysCard(0)
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("5")
        nonZeroDaysCard.titleView().check("days without alcohol")

        var zeroDaysCard = zeroDaysCard(1)
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without smoking")

        nonZeroDaysCard.editButton().click()

        nonZeroDaysCard.cardView().checkDoesNotExist()
        val nonZeroDaysEditCard = nonZeroDaysEditCard(0)
        nonZeroDaysEditCard.cardView().checkDisplayed()
        nonZeroDaysEditCard.daysView().check("5")
        nonZeroDaysEditCard.inputView().check("days without alcohol")
        nonZeroDaysEditCard.saveButton().checkDisplayed()
        nonZeroDaysEditCard.resetButton().checkDisplayed()

        nonZeroDaysEditCard.deleteButton().click()
        nonZeroDaysEditCard.deleteButton().click()

        nonZeroDaysEditCard.cardView().checkDoesNotExist()
        zeroDaysCard = zeroDaysCard(0)
        zeroDaysCard.cardView().checkDisplayed()
        val addCard = addCard(1)
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without smoking")
        addCard.cardView().checkDisplayed()
    }
}