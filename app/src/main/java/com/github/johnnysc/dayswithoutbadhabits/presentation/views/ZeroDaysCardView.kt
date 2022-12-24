package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 18.12.2022
 */
class ZeroDaysCardView : AbstractCardView.AbleToMove {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private lateinit var card: Card

    fun setUp(text: String, card: Card.ZeroDays) {
        this.card = card
        inflate(context, R.layout.zero_days_card_view, this)
        findViewById<TextView>(R.id.zeroDaysTitleTextView).text = text
        findViewById<View>(R.id.zeroDaysEditButton).setOnClickListener {
            hideAnimation {
                actions.editZeroDaysCard(positionCallback.position(this), card)
            }
        }
        animateStart()
    }

    override fun save() = SaveAndRestoreCard(card)
}