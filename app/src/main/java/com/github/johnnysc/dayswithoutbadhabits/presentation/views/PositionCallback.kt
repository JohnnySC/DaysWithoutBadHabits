package com.github.johnnysc.dayswithoutbadhabits.presentation.views

/**
 * @author Asatryan on 18.12.2022
 */
interface PositionCallback {

    fun position(cardUi: AbstractCardView): Int
    fun moveUp(position: Int)
    fun moveDown(position: Int)

    class Empty : PositionCallback {
        override fun position(cardUi: AbstractCardView): Int = -1
        override fun moveUp(position: Int) = Unit
        override fun moveDown(position: Int) = Unit
    }
}