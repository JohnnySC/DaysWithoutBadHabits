package com.github.johnnysc.dayswithoutbadhabits.presentation

interface ShowCardsNavigationButtons {

    fun show()

    class Base(private val list: List<CanBeMoved>) : ShowCardsNavigationButtons {

        override fun show() {
            val moveCardsList = list.filter { it.canBeMoved() }
            if (moveCardsList.size > 1) {
                moveCardsList.forEachIndexed { index, view ->
                    view.hideCanBeMoved()
                    when (index) {
                        0 -> view.showCanBeMovedDown()
                        moveCardsList.size - 1 -> view.showCanBeMovedUp()
                        else -> view.showCanBeMovedUpAndDown()
                    }
                }
            } else if (moveCardsList.isNotEmpty()) moveCardsList[0].hideCanBeMoved()
        }
    }
}