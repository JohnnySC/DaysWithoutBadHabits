package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.*
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 24.12.2022
 */
class MoveTwoCardsTest : AbstractUiTest() {
    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(
                mutableListOf(
                    CardCache(
                        1L,
                        now.time() - 3 * 24 * 3600 * 1000,
                        "days without alcohol"
                    ),
                    CardCache(
                        2L,
                        now.time() - 2 * 24 * 3600 * 1000,
                        "days without smoking"
                    )
                )
            )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val firstCard = nonZeroDaysCard(0)
        val secondCard = nonZeroDaysCard(1)

        firstCard.cardView().checkDisplayed()
        firstCard.daysView().check("3")
        firstCard.titleView().check("days without alcohol")
        secondCard.cardView().checkDisplayed()
        secondCard.daysView().check("2")
        secondCard.titleView().check("days without smoking")

        firstCard.editButton().click()
        firstCard.cardView().checkDoesNotExist()

        val editedFirstCard = nonZeroDaysEditCard(0)
        editedFirstCard.cardView().checkDisplayed()
        editedFirstCard.downButton().checkDisplayed()
        editedFirstCard.upButton().checkNotDisplayed()

        secondCard.editButton().click()
        secondCard.cardView().checkDoesNotExist()

        val editedSecondCard = nonZeroDaysEditCard(1)
        editedSecondCard.cardView().checkDisplayed()
        editedSecondCard.downButton().checkNotDisplayed()
        editedSecondCard.upButton().checkDisplayed()

        editedSecondCard.upButton().click()

        editedFirstCard.cardView().checkDisplayed()
        editedSecondCard.cardView().checkDisplayed()

        editedSecondCard.daysView().check("3")
        editedSecondCard.inputView().check("days without alcohol")
        editedFirstCard.daysView().check("2")
        editedFirstCard.inputView().check("days without smoking")

        editedFirstCard.upButton().checkNotDisplayed()
        editedFirstCard.downButton().checkDisplayed()
        editedSecondCard.upButton().checkDisplayed()
        editedSecondCard.downButton().checkNotDisplayed()
    }
}