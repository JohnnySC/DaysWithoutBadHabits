package com.github.johnnysc.dayswithoutbadhabits

import android.content.SharedPreferences
import org.junit.Test

/**
 * @author Asatryan on 15.12.2022
 */
class MainActivityNotZeroTest : AbstractUiTest() {

    override fun init(sharedPref: SharedPreferences) {
        CacheDataSource.Base(sharedPref).save(System.currentTimeMillis() - 17L * 24 * 3600 * 1000)
    }

    @Test
    fun test_n_days_and_reset() {
        MainPage().run {
            mainText.check("17")
            resetButton.checkDisplayed()
            resetButton.click()
            mainText.check("0")
            resetButton.checkNotDisplayed()
        }
    }
}