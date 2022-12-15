package com.github.johnnysc.dayswithoutbadhabits

import android.content.Context
import android.content.SharedPreferences

interface SharedPref {

    fun make(context: Context): SharedPreferences

    abstract class Abstract(
        private val name: String,
        private val mode: Int = Context.MODE_PRIVATE
    ) : SharedPref {

        override fun make(context: Context): SharedPreferences =
            context.getSharedPreferences(name, mode)
    }

    class Base : Abstract("release")
    class Test : Abstract("uiTest")

    class Factory(private val useTest: Boolean) : SharedPref {

        override fun make(context: Context): SharedPreferences =
            (if (useTest) Test() else Base()).make(context)
    }
}