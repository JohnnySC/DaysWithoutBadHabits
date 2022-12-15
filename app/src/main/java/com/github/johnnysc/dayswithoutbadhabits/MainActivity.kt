package com.github.johnnysc.dayswithoutbadhabits

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.mainTextView)
        val resetButton = findViewById<Button>(R.id.resetButton)

        val sharedPref = getSharedPreferences("main", Context.MODE_PRIVATE)
        val time = sharedPref.getLong("time", -1)
        if (time == -1L) {
            sharedPref.edit().putLong("time", System.currentTimeMillis()).apply()
            textView.text = "0"
            resetButton.visibility = View.GONE
        } else {
            val diff = (System.currentTimeMillis() - time) / 1000
            val days = diff / (24 * 3600)
            if (days == 0L)
                resetButton.visibility = View.GONE
            textView.text = days.toString()
        }

        resetButton.setOnClickListener {
            sharedPref.edit().putLong("time", System.currentTimeMillis()).apply()
            textView.text = "0"
            resetButton.visibility = View.GONE
        }
    }
}