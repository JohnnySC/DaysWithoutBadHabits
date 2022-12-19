package com.github.johnnysc.dayswithoutbadhabits.presentation.views

/**
 * @author Asatryan on 18.12.2022
 */
interface PositionCallback {

    fun position(cardUi: AbstractCardView): Int

    class Empty : PositionCallback {
        override fun position(cardUi: AbstractCardView): Int = -1
    }
}