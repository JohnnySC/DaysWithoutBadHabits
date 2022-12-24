package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.presentation.views.PositionCallback

/**
 * @author Asatryan on 18.12.2022
 */
interface CardUi : CanBeMoved {
    fun init(positionCallback: PositionCallback, actions: CardActions)

    fun clear()
}