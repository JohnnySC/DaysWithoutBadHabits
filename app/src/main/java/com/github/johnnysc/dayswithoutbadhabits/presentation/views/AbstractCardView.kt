package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardUi
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardActions

/**
 * @author Asatryan on 18.12.2022
 */
abstract class AbstractCardView : FrameLayout, CardUi {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    protected var positionCallback: PositionCallback = PositionCallback.Empty()
    protected var actions: CardActions = CardActions.Empty()

    override fun init(positionCallback: PositionCallback, actions: CardActions) {
        this.actions = actions
        this.positionCallback = positionCallback
    }

    override fun clear() {
        positionCallback = PositionCallback.Empty()
        actions = CardActions.Empty()
    }
}