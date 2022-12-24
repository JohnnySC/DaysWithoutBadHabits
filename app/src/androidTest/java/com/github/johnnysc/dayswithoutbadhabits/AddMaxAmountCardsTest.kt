package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.CacheDataSource
import com.github.johnnysc.dayswithoutbadhabits.data.Serialization
import com.github.johnnysc.dayswithoutbadhabits.data.StringStorage
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class AddMaxAmountCardsTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(ArrayList())
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        var addCard = addCard(0)
        addCard.cardView().checkDisplayed()

        addCard.cardView().click()
        var makeCard = makeCard(0)

        makeCard.cardView().checkDisplayed()
        addCard.cardView().checkDoesNotExist()

        makeCard.inputView().type("days without alcohol")
        makeCard.saveButton().click()
        var zeroDaysCard = zeroDaysCard(0)
        addCard = addCard(1)

        makeCard.cardView().checkDoesNotExist()
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without alcohol")
        addCard.cardView().checkDisplayed()

        addCard.cardView().click()

        addCard.cardView().checkDoesNotExist()
        makeCard = makeCard(1)
        makeCard.cardView().checkDisplayed()

        makeCard.inputView().type("days without smoking")
        makeCard.saveButton().click()

        makeCard.cardView().checkDoesNotExist()
        zeroDaysCard = zeroDaysCard(1)
        zeroDaysCard.cardView().checkDisplayed()
        zeroDaysCard.daysView().check("0")
        zeroDaysCard.titleView().check("days without smoking")
        addCard.cardView().checkDoesNotExist()
    }
}