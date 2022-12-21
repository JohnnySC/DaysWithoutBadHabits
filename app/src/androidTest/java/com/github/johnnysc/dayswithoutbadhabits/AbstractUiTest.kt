package com.github.johnnysc.dayswithoutbadhabits

import android.content.Intent
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.johnnysc.dayswithoutbadhabits.data.SharedPref
import com.github.johnnysc.dayswithoutbadhabits.presentation.MainActivity
import org.hamcrest.CoreMatchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

/**
 * @author Asatryan on 15.12.2022
 */
@RunWith(AndroidJUnit4::class)
abstract class AbstractUiTest {

    @get:Rule
    val activityScenarioRule = lazyActivityScenarioRule<MainActivity>(launchActivity = false)

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<App>()
        val sharedPref = SharedPref.Test().make(context)
        init(sharedPref)
        activityScenarioRule.launch(Intent(context, MainActivity::class.java))
    }

    protected abstract fun init(sharedPref: SharedPreferences)

    protected fun ViewInteraction.check(text: String) = check(matches(withText(text)))
    protected fun ViewInteraction.click() = perform(ViewActions.click())
    protected fun ViewInteraction.checkDisplayed() = check(matches(ViewMatchers.isDisplayed()))
    protected fun ViewInteraction.checkNotDisplayed() =
        check(matches(not(ViewMatchers.isDisplayed())))

    protected fun ViewInteraction.checkDoesNotExist() = check(doesNotExist())
    protected fun ViewInteraction.type(text: String) =
        perform(replaceText(text), closeSoftKeyboard())
}