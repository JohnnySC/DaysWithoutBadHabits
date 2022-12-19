package com.github.johnnysc.dayswithoutbadhabits.presentation

import com.github.johnnysc.dayswithoutbadhabits.presentation.views.PositionCallback

/**
 * @author Asatryan on 16.12.2022
 */
interface CardsContainer<T : CardUi> : PositionCallback {

    fun add(index: Int, view: T)

    fun add(view: T)

    fun replace(index: Int, view: T)

    fun remove(index: Int)
}