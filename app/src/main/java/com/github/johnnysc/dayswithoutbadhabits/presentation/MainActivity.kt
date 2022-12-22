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

    private lateinit var cardsLayout: CardsLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = (application as ProvideViewModel).provideMainViewModel()

        val makeUi = MakeUi.Base(this)
        cardsLayout = findViewById(R.id.cardsLayout)
        viewModel.observe(this) {
            it.apply(cardsLayout, makeUi, viewModel)
        }
        viewModel.init(savedInstanceState == null)
        cardsLayout.restore(savedInstanceState, makeUi, viewModel)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        cardsLayout.save(outState)
    }
}