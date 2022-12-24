package com.github.johnnysc.dayswithoutbadhabits.presentation

interface CanBeMoved {
    fun canBeMoved(): Boolean

    fun hideCanBeMoved()
    fun showCanBeMovedDown()
    fun showCanBeMovedUp()
    fun showCanBeMovedUpAndDown() {
        showCanBeMovedUp()
        showCanBeMovedDown()
    }
}