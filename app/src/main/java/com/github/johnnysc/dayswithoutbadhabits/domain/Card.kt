package com.github.johnnysc.dayswithoutbadhabits.domain

/**
 * @author Asatryan on 17.12.2022
 */
sealed class Card(
    private val id: Long,
    private val text: String,
    private val days: Int,
    private val editable: Boolean
) {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, text, days, editable)

    interface Mapper<T> {
        fun map(id: Long, text: String, days: Int, editable: Boolean): T

        class ResetDays : Mapper<Card> {
            override fun map(id: Long, text: String, days: Int, editable: Boolean) =
                ZeroDays(text, id)
        }

        class Same(private val id: Long) : Mapper<Boolean> {
            override fun map(id: Long, text: String, days: Int, editable: Boolean) =
                id == this.id
        }

        class Duplicate(private val newText: String) : Mapper<Card> {
            override fun map(id: Long, text: String, days: Int, editable: Boolean): Card {
                return if (days > 0) {
                    if (editable)
                        NonZeroDaysEdit(days, newText, id)
                    else
                        NonZeroDays(days, newText, id)

                } else if (days == 0) {
                    if (editable)
                        ZeroDaysEdit(newText, id)
                    else
                        ZeroDays(newText, id)
                } else {
                    if (id == 0L)
                        Add
                    else
                        Make
                }
            }
        }

        class ChangeEditable : Mapper<Card> {
            override fun map(id: Long, text: String, days: Int, editable: Boolean): Card {
                return if (days > 0) {
                    if (editable)
                        NonZeroDays(days, text, id)
                    else
                        NonZeroDaysEdit(days, text, id)
                } else if (days == 0) {
                    if (editable)
                        ZeroDays(text, id)
                    else
                        ZeroDaysEdit(text, id)
                } else {
                    if (id == 0L)
                        Add
                    else
                        Make
                }
            }
        }

        class Reset(private val resetCard: ResetCard) : Mapper<Unit> {
            override fun map(id: Long, text: String, days: Int, editable: Boolean) =
                resetCard.resetCard(id)
        }
    }

    object Add : Card(0L, "", -1, false)

    object Make : Card(1L, "", -1, false)

    data class ZeroDays(val text: String, val id: Long) : Card(id, text, 0, false)

    data class ZeroDaysEdit(val text: String, val id: Long) : Card(id, text, 0, true)

    data class NonZeroDays(val days: Int, val text: String, val id: Long) :
        Card(id, text, days, false)

    data class NonZeroDaysEdit(val days: Int, val text: String, val id: Long) :
        Card(id, text, days, true)
}