package com.github.johnnysc.dayswithoutbadhabits

import android.app.Application
import androidx.lifecycle.MutableLiveData

/**
 * @author Asatryan on 15.12.2022
 */
class App : Application(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        val sharedPref = if (BuildConfig.DEBUG) SharedPref.Test() else SharedPref.Base()
        viewModel = MainViewModel(
            MainRepository.Base(
                CacheDataSource.Base(sharedPref.make(this)),
                Now.Base()
            ),
            MainCommunication.Base(MutableLiveData())
        )
    }

    override fun provideMainViewModel(): MainViewModel {
        return viewModel
    }
}

interface ProvideViewModel {

    fun provideMainViewModel(): MainViewModel
}