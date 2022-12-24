package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.*
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class DeleteNonZeroDaysCardWhenAddCardPresentTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(
                mutableListOf(
                    CardCache(
                        1L,
                        now.time() - 3 * 24 * 3600 * 1000,
                        "days without alcohol"
                    )
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val nonZeroDaysCard = nonZeroDaysCard(0)
        nonZeroDaysCard.cardView().checkDisplayed()
        nonZeroDaysCard.daysView().check("3")
        nonZeroDaysCard.titleView().check("days without alcohol")
        var addCard = addCard(1)
        addCard.cardView().checkDisplayed()

        nonZeroDaysCard.editButton().click()

        addCard.cardView().checkDisplayed()
        nonZeroDaysCard.cardView().checkDoesNotExist()
        val nonZeroDaysEditCard = nonZeroDaysEditCard(0)
        nonZeroDaysEditCard.cardView().checkDisplayed()
        nonZeroDaysEditCard.daysView().check("3")
        nonZeroDaysEditCard.inputView().check("days without alcohol")
        nonZeroDaysEditCard.saveButton().checkDisplayed()

        nonZeroDaysEditCard.deleteButton().click()
        nonZeroDaysEditCard.saveButton().checkNotDisplayed()
        nonZeroDaysEditCard.resetButton().checkNotDisplayed()

        nonZeroDaysEditCard.cancelButton().click()
        nonZeroDaysEditCard.saveButton().checkDisplayed()
        nonZeroDaysEditCard.resetButton().checkDisplayed()

        nonZeroDaysEditCard.deleteButton().click()
        nonZeroDaysEditCard.saveButton().checkNotDisplayed()
        nonZeroDaysEditCard.resetButton().checkNotDisplayed()

        nonZeroDaysEditCard.deleteButton().click()

        nonZeroDaysEditCard.cardView().checkDoesNotExist()
        nonZeroDaysCard.cardView().checkDoesNotExist()
        addCard = addCard(0)
        addCard.cardView().checkDisplayed()
    }
}