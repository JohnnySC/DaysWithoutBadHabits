package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import com.github.johnnysc.dayswithoutbadhabits.data.CacheDataSource
import com.google.gson.Gson
import org.junit.Test

/**
 * @author Asatryan on 20.12.2022
 */
class InitialStateAddCardClickTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        CacheDataSource.Base(sharedPref, Gson()).save(ArrayList())
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        val addCard = addCard(0)
        addCard.cardView().checkDisplayed()
        addCard.cardView().click()
        addCard.cardView().checkDoesNotExist()

        val makeCard = makeCard(0)
        makeCard.cardView().checkDisplayed()
    }
}