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
class CancelMakeCardTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(ArrayList())
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val addCard = addCard(0)
        addCard.cardView().checkDisplayed()

        addCard.cardView().click()
        val makeCard = makeCard(0)
        addCard.cardView().checkDoesNotExist()
        makeCard.cardView().checkDisplayed()

        makeCard.cancelButton().click()
        makeCard.cardView().checkDoesNotExist()
        addCard.cardView().checkDisplayed()
    }
}