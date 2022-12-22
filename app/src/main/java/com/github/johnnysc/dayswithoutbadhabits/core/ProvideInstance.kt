package com.github.johnnysc.dayswithoutbadhabits.core

import com.github.johnnysc.dayswithoutbadhabits.data.SharedPref

/**
 * @author Asatryan on 21.12.2022
 */
interface ProvideInstance {

    fun sharedPref(): SharedPref

    fun maxCount(): Int

    class Base(isDebug: Boolean) : ProvideInstance {

        private val provideInstance = if (isDebug)
            Debug()
        else
            Release()

        override fun sharedPref() = provideInstance.sharedPref()
        override fun maxCount() = provideInstance.maxCount()
    }

    private class Debug : ProvideInstance {
        override fun sharedPref() = SharedPref.Test()
        override fun maxCount(): Int = 2
    }

    private class Release : ProvideInstance {
        override fun sharedPref() = SharedPref.Base()
        override fun maxCount(): Int = 3
    }
}
