package com.github.johnnysc.dayswithoutbadhabits

import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * @author Asatryan on 15.12.2022
 */
sealed class UiState {

    abstract fun apply(daysTextView: TextView, resetButton: Button)

    object ZeroDays : UiState() {
        override fun apply(daysTextView: TextView, resetButton: Button) {
            daysTextView.text = "0"
            resetButton.visibility = View.GONE
        }
    }

    data class NDays(private val days: Int) : UiState() {
        override fun apply(daysTextView: TextView, resetButton: Button) {
            daysTextView.text = days.toString()
            resetButton.visibility = View.VISIBLE
        }
    }
}