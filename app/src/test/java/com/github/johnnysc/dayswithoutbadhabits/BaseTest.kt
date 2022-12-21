package com.github.johnnysc.dayswithoutbadhabits

import com.github.johnnysc.dayswithoutbadhabits.data.Now

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
}