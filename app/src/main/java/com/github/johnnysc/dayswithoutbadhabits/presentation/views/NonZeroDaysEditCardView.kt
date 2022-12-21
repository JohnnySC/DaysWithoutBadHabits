package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText

/**
 * @author Asatryan on 19.12.2022
 */
class NonZeroDaysEditCardView : AbstractCardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var deletePressed = false
    private var resetPressed = false

    fun setUp(id: Long, days: Int, text: String, card: Card.NonZeroDaysEdit) {
        inflate(context, R.layout.non_zero_days_edit_card_view, this)
        val deleteButton = findViewById<Button>(R.id.deleteButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val cancelButton = findViewById<Button>(R.id.cancelButton)
        val input = findViewById<TextInputEditText>(R.id.inputEditText)

        findViewById<TextView>(R.id.daysTextView).text = days.toString()
        input.setText(text)
        input.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                saveButton.isEnabled = text != s.toString()
            }
        })

        deleteButton.setOnClickListener {
            if (deletePressed)
                closeAnimation {
                    actions.deleteCard(positionCallback.position(this), id)
                }
            else {
                saveButton.visibility = View.GONE
                resetButton.visibility = View.GONE
                deleteButton.setText(R.string.confirm_delete_card)
                deletePressed = true
            }
        }

        resetButton.setOnClickListener {
            if (resetPressed)
                hideAnimation {
                    actions.resetNonZeroDaysCard(positionCallback.position(this), card)
                }
            else {
                resetPressed = true
                resetButton.setText(R.string.confirm_reset_days)
                deleteButton.visibility = View.GONE
                saveButton.visibility = View.GONE
            }
        }

        saveButton.setOnClickListener {
            hideAnimation {
                actions.saveEditedNonZeroDaysCard(
                    days, input.text.toString(), positionCallback.position(this), id
                )
            }
        }

        cancelButton.setOnClickListener {
            if (deletePressed) {
                deletePressed = false
                deleteButton.setText(R.string.delete_card)
                saveButton.visibility = View.VISIBLE
                resetButton.visibility = View.VISIBLE
            } else if (resetPressed) {
                resetPressed = false
                resetButton.setText(R.string.reset_card)
                saveButton.visibility = View.VISIBLE
                deleteButton.visibility = View.VISIBLE
            } else
                hideAnimation {
                    actions.cancelEditNonZeroDaysCard(positionCallback.position(this), card)
                }
        }
        animateStart()
    }
}