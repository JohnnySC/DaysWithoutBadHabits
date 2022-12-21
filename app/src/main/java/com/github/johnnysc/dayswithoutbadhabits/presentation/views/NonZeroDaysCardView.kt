package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 19.12.2022
 */
class NonZeroDaysCardView : AbstractCardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setUp(days: String, text: String, card: Card.NonZeroDays) {
        inflate(context, R.layout.non_zero_days_card_view, this)
        findViewById<TextView>(R.id.daysTextView).text = days
        findViewById<TextView>(R.id.titleTextView).text = text
        findViewById<View>(R.id.editButton).setOnClickListener {
            hideAnimation {
                actions.editNonZeroDaysCard(positionCallback.position(this), card)
            }
        }
        animateStart()
    }
}