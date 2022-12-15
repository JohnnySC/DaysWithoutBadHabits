package com.github.johnnysc.dayswithoutbadhabits

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = (application as ProvideViewModel).provideMainViewModel()

        val textView = findViewById<TextView>(R.id.mainTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)

        viewModel.observe(this) { uiState ->
            uiState.apply(textView, resetButton)
        }

        resetButton.setOnClickListener {
            viewModel.reset()
        }
        viewModel.init(savedInstanceState == null)
    }
}