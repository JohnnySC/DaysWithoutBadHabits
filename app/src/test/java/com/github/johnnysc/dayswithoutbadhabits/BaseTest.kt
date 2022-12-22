package com.github.johnnysc.dayswithoutbadhabits

import com.github.johnnysc.dayswithoutbadhabits.data.Now
import com.github.johnnysc.dayswithoutbadhabits.domain.Card

/**
 * @author Asatryan on 18.12.2022
 */
abstract class BaseTest {

    protected interface FakeNow : Now {

        fun addTime(diff: Long)

        class Base : FakeNow {

            private var time = 0L

            override fun time(): Long {
                return time
            }

            override fun addTime(diff: Long) {
                this.time += diff
            }
        }
    }

    protected class SameCardMapper(private val id: Long) : Card.Mapper<Boolean> {
        override fun map(id: Long, text: String, days: Int, editable: Boolean) =
            id == this.id
    }

    class DuplicateCardMapper(private val newText: String) : Card.Mapper<Card.Abstract> {
        override fun map(id: Long, text: String, days: Int, editable: Boolean): Card.Abstract {
            return if (days > 0) {
                if (editable)
                    Card.NonZeroDaysEdit(days, newText, id)
                else
                    Card.NonZeroDays(days, newText, id)

            } else if (days == 0) {
                if (editable)
                    Card.ZeroDaysEdit(newText, id)
                else
                    Card.ZeroDays(newText, id)
            } else {
                if (id == 0L)
                    Card.Add
                else
                    Card.Make
            }
        }
    }
}