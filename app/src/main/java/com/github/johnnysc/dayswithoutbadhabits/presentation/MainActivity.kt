package com.github.johnnysc.dayswithoutbadhabits.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.johnnysc.dayswithoutbadhabits.ProvideViewModel
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.presentation.views.CardsLayout

/**
 * @author Asatryan on 18.12.2022
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = (application as ProvideViewModel).provideMainViewModel()

        val makeUi = MakeUi.Base(this)
        val viewGroup = findViewById<CardsLayout>(R.id.customViewGroup)
        viewModel.observe(this) {
            it.apply(viewGroup, makeUi, viewModel)
        }
        viewModel.init(savedInstanceState == null)
    }
}