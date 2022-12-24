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
class InitialEmptyStateTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        CacheDataSource.Base(StringStorage.Base(sharedPref), Serialization.Base(Gson()))
            .save(ArrayList())
    }

    @Test
    fun test(): Unit = with(MainPage()) {
        addCard(0).cardView().checkDisplayed()
    }
}