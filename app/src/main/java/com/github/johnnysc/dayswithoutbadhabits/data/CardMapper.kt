package com.github.johnnysc.dayswithoutbadhabits.data

import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 18.12.2022
 */
interface CardMapper<T> {
    fun map(
        id: Long,
        countStartTime: Long,
        text: String
    ): T

    class Base(private val now: Now) : CardMapper<Card> {

        override fun map(id: Long, countStartTime: Long, text: String): Card {
            val diff = now.time() - countStartTime
            val days = (diff / (24 * 3600 * 1000)).toInt()
            return if (days > 0)
                Card.NonZeroDays(days, text, id)
            else
                Card.ZeroDays(text, id)
        }
    }

    class Same(private val id: Long) : CardMapper<Boolean> {
        override fun map(id: Long, countStartTime: Long, text: String) =
            this.id == id
    }

    class UpdateText(private val newText: String) : CardMapper<CardCache> {
        override fun map(id: Long, countStartTime: Long, text: String) =
            CardCache(id, countStartTime, newText)
    }

    class UpdateTime(private val newCountStartTime: Long) : CardMapper<CardCache> {
        override fun map(id: Long, countStartTime: Long, text: String) =
            CardCache(id, newCountStartTime, text)
    }
}