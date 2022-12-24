package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.*
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class DeleteZeroDaysCardWhenAddCardNotPresentTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(
                mutableListOf(
                    CardCache(1L, now.time(), "days without alcohol"),
                    CardCache(2L, now.time() - 60000, "days without smoking")
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val zeroDaysCardOne = zeroDaysCard(0)
        val zeroDaysCardTwo = zeroDaysCard(1)
        zeroDaysCardOne.cardView().checkDisplayed()
        zeroDaysCardOne.daysView().check("0")
        zeroDaysCardOne.titleView().check("days without alcohol")

        zeroDaysCardTwo.cardView().checkDisplayed()
        zeroDaysCardTwo.daysView().check("0")
        zeroDaysCardTwo.titleView().check("days without smoking")

        zeroDaysCardOne.editButton().click()

        zeroDaysCardOne.cardView().checkDoesNotExist()
        val zeroDaysEditCard = zeroDaysEditCard(0)
        zeroDaysEditCard.cardView().checkDisplayed()
        zeroDaysEditCard.inputView().check("days without alcohol")
        zeroDaysEditCard.saveButton().checkDisplayed()

        zeroDaysEditCard.deleteButton().click()
        zeroDaysEditCard.deleteButton().click()

        zeroDaysEditCard.cardView().checkDoesNotExist()
        zeroDaysCardOne.cardView().checkDisplayed()
        zeroDaysCardOne.daysView().check("0")
        zeroDaysCardOne.titleView().check("days without smoking")
        val addCard = addCard(1)
        addCard.cardView().checkDisplayed()
    }
}