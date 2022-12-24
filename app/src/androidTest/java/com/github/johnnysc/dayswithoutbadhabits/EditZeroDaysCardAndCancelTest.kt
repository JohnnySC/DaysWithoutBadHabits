package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.*
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class EditZeroDaysCardAndCancelTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(mutableListOf(CardCache(1L, now.time(), "days without alcohol")))
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
        zeroDaysEditCard.inputView().type("some text here")

        zeroDaysEditCard.cancelButton().click()

        zeroDaysEditCard.cardView().checkDoesNotExist()
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without alcohol")
        addCard.cardView().checkDisplayed()
    }
}