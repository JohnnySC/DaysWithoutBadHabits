package com.github.johnnysc.dayswithoutbadhabits

import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * @author Asatryan on 15.12.2022
 */
sealed class UiState {

    abstract fun apply(daysTextView: TextView, resetButton: Button)

    abstract class Abstract(private val text: String, private val visibility: Int) : UiState() {
        override fun apply(daysTextView: TextView, resetButton: Button) {
            daysTextView.text = text
            resetButton.visibility = visibility
        }
    }

    object ZeroDays : Abstract("0", View.GONE)
    data class NDays(private val days: Int) : Abstract(days.toString(), View.VISIBLE)
}