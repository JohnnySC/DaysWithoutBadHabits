package com.github.johnnysc.dayswithoutbadhabits.presentation

import android.content.Context
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.views.*

interface MakeUi<T> {

    fun addCard(card: Card.Add): T
    fun makeCard(card: Card.Make): T
    fun zeroDays(text: String, card: Card.ZeroDays): T
    fun editableZeroDays(id: Long, text: String, card: Card.ZeroDaysEdit): T
    fun nonZeroDays(days: Int, text: String, card: Card.NonZeroDays): T
    fun editableNonZeroDays(id: Long, days: Int, text: String, card: Card.NonZeroDaysEdit): T

    class Base(private val context: Context) : MakeUi<AbstractCardView> {

        override fun addCard(card: Card.Add) = AddButtonView(context)

        override fun makeCard(card: Card.Make) = MakeCardView(context)

        override fun zeroDays(text: String, card: Card.ZeroDays) = ZeroDaysCardView(context).apply {
            setUp(text, card)
        }

        override fun editableZeroDays(
            id: Long,
            text: String,
            card: Card.ZeroDaysEdit
        ) = ZeroDaysEditCardView(context).apply {
            setUp(id, text, card)
        }

        override fun nonZeroDays(
            days: Int,
            text: String,
            card: Card.NonZeroDays
        ) = NonZeroDaysCardView(context).apply {
            setUp(days.toString(), text, card)
        }

        override fun editableNonZeroDays(
            id: Long,
            days: Int,
            text: String,
            card: Card.NonZeroDaysEdit
        ) = NonZeroDaysEditCardView(context).apply {
            setUp(id, days, text, card)
        }
    }
}