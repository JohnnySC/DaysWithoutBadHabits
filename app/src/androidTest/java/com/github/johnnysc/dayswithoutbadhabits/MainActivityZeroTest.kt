package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import org.junit.Test

/**
 * @author Asatryan on 15.12.2022
 */
class MainActivityZeroTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        sharedPref.edit().clear().apply()
    }

    @Test
    fun test_n_days_and_reset() {
        MainPage().run {
            mainText.check("0")
            resetButton.checkNotDisplayed()
        }
    }
}
