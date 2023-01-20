package com.github.johnnysc.dayswithoutbadhabits.domain

import com.github.johnnysc.dayswithoutbadhabits.presentation.MakeUi
import java.io.Serializable

/**
 * @author Asatryan on 17.12.2022
 */
interface Card : Serializable {

    fun <T> make(makeUi: MakeUi<T>): T

    abstract class Abstract(
        protected open val id: Long,
        protected open val text: String,
        protected open val days: Int,
        protected open val editable: Boolean
    ) : Card {

        fun <T> map(mapper: Mapper<T>): T = mapper.map(id, text, days, editable)
    }

    interface Mapper<T> {
        fun map(id: Long, text: String, days: Int, editable: Boolean): T

        class ResetDays : Mapper<Abstract> {
            override fun map(id: Long, text: String, days: Int, editable: Boolean) =
                ZeroDays(text, id)
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

    object Add : Abstract(0L, "", -1, false) {
        override fun <T> make(makeUi: MakeUi<T>): T = makeUi.addCard(this)
    }

    object Make : Abstract(1L, "", -1, false) {
        override fun <T> make(makeUi: MakeUi<T>): T = makeUi.makeCard(this)
    }

    data class ZeroDays(
        override val text: String, override val id: Long
    ) : Abstract(id, text, 0, false) {
        override fun <T> make(makeUi: MakeUi<T>): T = makeUi.zeroDays(text, this)
    }

    data class ZeroDaysEdit(
        override val text: String, override val id: Long
    ) : Abstract(id, text, 0, true) {
        override fun <T> make(makeUi: MakeUi<T>): T = makeUi.editableZeroDays(id, text, this)
    }

    data class NonZeroDays(
        override val days: Int,
        override val text: String,
        override val id: Long
    ) : Abstract(id, text, days, false) {

        override fun <T> make(makeUi: MakeUi<T>): T = makeUi.nonZeroDays(days, text, this)
    }

    data class NonZeroDaysEdit(
        override val days: Int,
        override val text: String,
        override val id: Long
    ) : Abstract(id, text, days, true) {

        override fun <T> make(makeUi: MakeUi<T>): T =
            makeUi.editableNonZeroDays(id, days, text, this)
    }
}