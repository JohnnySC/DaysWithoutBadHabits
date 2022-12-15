package com.github.johnnysc.dayswithoutbadhabits

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers

/**
 * @author Asatryan on 15.12.2022
 */
abstract class AbstractPage {
    protected fun Int.view(): ViewInteraction = Espresso.onView(ViewMatchers.withId(this))
}