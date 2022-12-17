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
        viewModel = MainViewModel(
            MainRepository.Base(
                CacheDataSource.Base(SharedPref.Factory(BuildConfig.DEBUG).make(this)),
                Now.Base()
            ),
            MainCommunication.Base()
        )
    }

    override fun provideMainViewModel(): MainViewModel {
        return viewModel
    }
}

interface ProvideViewModel {

    fun provideMainViewModel(): MainViewModel
}