package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.*
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class EditNonZeroDaysCardAndSaveTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
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
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("8")
        nonZeroDaysCard.titleView().check("days without alcohol")
        val addCard = addCard(1)
        addCard.cardView().checkDisplayed()

        nonZeroDaysCard.editButton().click()

        nonZeroDaysCard.cardView().checkDoesNotExist()
        val nonZeroDaysEditCard = nonZeroDaysEditCard(0)
        nonZeroDaysEditCard.cardView().checkDisplayed()
        nonZeroDaysEditCard.daysView().check("8")
        nonZeroDaysEditCard.inputView().check("days without alcohol")
        nonZeroDaysEditCard.saveButton().checkDisplayed()
        addCard.cardView().checkDisplayed()

        nonZeroDaysEditCard.inputView().type("new text")
        nonZeroDaysEditCard.saveButton().click()

        nonZeroDaysEditCard.cardView().checkDoesNotExist()
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("8")
        nonZeroDaysCard.titleView().check("new text")
        addCard.cardView().checkDisplayed()
    }
}