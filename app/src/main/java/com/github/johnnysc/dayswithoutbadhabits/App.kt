package com.github.johnnysc.dayswithoutbadhabits

import android.app.Application
import com.github.johnnysc.dayswithoutbadhabits.core.ProvideInstance
import com.github.johnnysc.dayswithoutbadhabits.data.BaseRepository
import com.github.johnnysc.dayswithoutbadhabits.data.CacheDataSource
import com.github.johnnysc.dayswithoutbadhabits.data.Now
import com.github.johnnysc.dayswithoutbadhabits.domain.MainInteractor
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
        val provideInstance = ProvideInstance.Base(BuildConfig.DEBUG)
        viewModel = MainViewModel(
            MainCommunication.Base(),
            MainInteractor.Base(
                BaseRepository(
                    CacheDataSource.Base(
                        provideInstance.sharedPref().make(this),
                        Gson()
                    ),
                    Now.Base(),
                ),
                provideInstance.maxCount()
            )
        )
    }

    override fun provideMainViewModel(): MainViewModel {
        return viewModel
    }
}

interface ProvideViewModel {

    fun provideMainViewModel(): MainViewModel
}