package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.CacheDataSource
import com.github.johnnysc.dayswithoutbadhabits.data.CardCache
import com.github.johnnysc.dayswithoutbadhabits.data.Now
import com.google.gson.Gson
import org.junit.Test

/**
 * Developer Options -> Remove animations
 * is required to run this test
 *
 * @author Asatryan on 22.12.2022
 */
class RotationCheckTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        val now = Now.Base()
        CacheDataSource.Base(sharedPref, Gson()).save(
            mutableListOf(
                CardCache(1L, now.time() - 10000L, "one"),
                CardCache(1L, now.time() - 20000L, "two"),
            )
        )
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val zeroDaysOne = zeroDaysCard(0)
        val zeroDaysTwo = zeroDaysCard(1)

        zeroDaysOne.cardView().checkDisplayed()
        zeroDaysOne.titleView().check("one")
        zeroDaysTwo.cardView().checkDisplayed()
        zeroDaysTwo.titleView().check("two")

        zeroDaysOne.editButton().click()
        zeroDaysOne.cardView().checkDoesNotExist()
        zeroDaysTwo.editButton().click()
        zeroDaysTwo.cardView().checkDoesNotExist()

        val zeroDaysOneEditable = zeroDaysEditCard(0)
        val zeroDaysTwoEditable = zeroDaysEditCard(1)

        zeroDaysOneEditable.cardView().checkDisplayed()
        zeroDaysTwoEditable.cardView().checkDisplayed()

        zeroDaysOneEditable.inputView().type("new text 1")
        zeroDaysTwoEditable.inputView().type("new text 2")

        rotateLeft()

        zeroDaysOneEditable.cardView().checkDisplayed()
        zeroDaysTwoEditable.cardView().checkDisplayed()

        zeroDaysOneEditable.inputView().check("new text 1")
        zeroDaysTwoEditable.inputView().check("new text 2")

        zeroDaysOneEditable.saveButton().click()
        zeroDaysTwoEditable.saveButton().click()

        zeroDaysOneEditable.cardView().checkDoesNotExist()
        zeroDaysTwoEditable.cardView().checkDoesNotExist()

        zeroDaysOne.cardView().checkDisplayed()
        zeroDaysOne.titleView().check("new text 1")
        zeroDaysTwo.cardView().checkDisplayed()
        zeroDaysTwo.titleView().check("new text 2")

        returnRotated()

        zeroDaysOne.cardView().checkDisplayed()
        zeroDaysOne.titleView().check("new text 1")
        zeroDaysTwo.cardView().checkDisplayed()
        zeroDaysTwo.titleView().check("new text 2")
    }
}