package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.*
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class EditNonZeroDaysCardAndCancelTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(
                mutableListOf(
                    CardCache(
                        1L,
                        now.time() - 2 * 24 * 3600 * 1000,
                        "days without alcohol"
                    )
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val nonZeroDaysCard = nonZeroDaysCard(0)
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("2")
        nonZeroDaysCard.titleView().check("days without alcohol")
        val addCard = addCard(1)
        addCard.cardView().checkDisplayed()

        nonZeroDaysCard.editButton().click()

        nonZeroDaysCard.cardView().checkDoesNotExist()
        val nonZeroDaysEditCard = nonZeroDaysEditCard(0)
        nonZeroDaysEditCard.cardView().checkDisplayed()
        nonZeroDaysEditCard.inputView().check("days without alcohol")
        nonZeroDaysEditCard.inputView().type("new text here")
        nonZeroDaysEditCard.daysView().check("2")
        nonZeroDaysEditCard.cancelButton().click()

        nonZeroDaysEditCard.cardView().checkDoesNotExist()
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("2")
        nonZeroDaysCard.titleView().check("days without alcohol")
        addCard.cardView().checkDisplayed()
    }
}