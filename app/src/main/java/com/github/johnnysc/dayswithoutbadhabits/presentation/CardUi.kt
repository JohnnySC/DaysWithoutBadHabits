package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.presentation.views.PositionCallback

/**
 * @author Asatryan on 18.12.2022
 */
interface CardUi {
    fun init(
        positionCallback: PositionCallback,
        actions: CardActions,
        animationEnabled: Boolean
    )

    fun clear()
}