package com.github.johnnysc.dayswithoutbadhabits

/**
 * @author Asatryan on 15.12.2022
 */
interface Now {
    fun time(): Long

    class Base : Now {
        override fun time() = System.currentTimeMillis()
    }
}