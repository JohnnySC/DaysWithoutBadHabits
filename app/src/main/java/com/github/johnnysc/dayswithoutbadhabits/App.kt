package com.github.johnnysc.dayswithoutbadhabits

import android.app.Application
import com.github.johnnysc.dayswithoutbadhabits.data.NewCacheDataSource
import com.github.johnnysc.dayswithoutbadhabits.data.NewRepository
import com.github.johnnysc.dayswithoutbadhabits.domain.NewMainInteractor
import com.github.johnnysc.dayswithoutbadhabits.presentation.MainCommunication
import com.github.johnnysc.dayswithoutbadhabits.presentation.MainViewModel
import com.google.gson.Gson

/**
 * @author Asatryan on 15.12.2022
 */
class App : Application(), ProvideViewModel {

    private lateinit var viewModel: MainViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel = MainViewModel(
            MainCommunication.Base(),
            NewMainInteractor.Base(
                NewRepository(
                    NewCacheDataSource.Base(
                        SharedPref.Factory(BuildConfig.DEBUG).make(this),
                        Gson()
                    ),
                    Now.Base(),
                ),
                3
            ),
        )
    }

    override fun provideMainViewModel(): MainViewModel {
        return viewModel
    }
}

interface ProvideViewModel {

    fun provideMainViewModel(): MainViewModel
}