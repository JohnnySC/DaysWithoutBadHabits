package com.github.johnnysc.dayswithoutbadhabits

/**
 * @author Asatryan on 15.12.2022
 */
interface MainRepository {
    fun reset()
    fun days(): Int
}