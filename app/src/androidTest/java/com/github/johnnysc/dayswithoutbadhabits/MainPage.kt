package com.github.johnnysc.dayswithoutbadhabits

import androidx.test.espresso.ViewInteraction

/**
 * @author Asatryan on 15.12.2022
 */
class MainPage : AbstractPage() {

    val mainText: ViewInteraction = R.id.mainTextView.view()
    val resetButton: ViewInteraction = R.id.resetButton.view()
}