package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.util.AttributeSet
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 18.12.2022
 */
class AddButtonView : AbstractCardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.add_button_view, this)
        setOnClickListener {
            hideAnimation {
                actions.addCard(positionCallback.position(this))
            }
        }
        animateStart()
    }

    override fun save() = SaveAndRestoreCard(Card.Add)
}