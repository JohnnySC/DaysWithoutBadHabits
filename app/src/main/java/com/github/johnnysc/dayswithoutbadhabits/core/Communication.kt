package com.github.johnnysc.dayswithoutbadhabits.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Asatryan on 17.12.2022
 */
interface Communication {

    interface Put<T> {
        fun put(value: T)
    }

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    abstract class Abstract<T>(
        private val liveData: MutableLiveData<T> = MutableLiveData()
    ) : Put<T>, Observe<T> {

        override fun put(value: T) {
            liveData.value = value
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) =
            liveData.observe(owner, observer)
    }
}