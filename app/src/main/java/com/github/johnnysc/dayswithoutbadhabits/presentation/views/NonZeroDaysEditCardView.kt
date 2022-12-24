package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable

/**
 * @author Asatryan on 19.12.2022
 */
class NonZeroDaysEditCardView : AbstractCardView.AbleToMove.Editable {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var deletePressed = false
    private var resetPressed = false
    private lateinit var input: TextInputEditText
    private lateinit var deleteButton: Button
    private lateinit var resetButton: Button
    private lateinit var saveButton: Button
    private lateinit var card: Card

    private var text = ""
    private val textWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: android.text.Editable?) {
            saveButton.isEnabled = text != s.toString()
        }
    }

    fun setUp(id: Long, days: Int, text: String, card: Card.NonZeroDaysEdit) {
        this.card = card
        this.text = text
        inflate(context, R.layout.non_zero_days_edit_card_view, this)
        setUpNavigationButtons()
        deleteButton = findViewById(R.id.nonZeroDaysEditDeleteButton)
        resetButton = findViewById(R.id.nonZeroDaysEditResetButton)
        saveButton = findViewById(R.id.nonZeroDaysEditSaveButton)
        val cancelButton = findViewById<Button>(R.id.nonZeroDaysEditCancelButton)
        input =
            (findViewById<LinearLayout>(R.id.nonZeroDaysLinearLayout).getChildAt(1) as TextInputEditText)

        findViewById<TextView>(R.id.nonZeroDaysEditTextView).text = days.toString()

        input.addTextChangedListener(textWatcher)
        input.setText(text)

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

    override fun save(): SaveAndRestoreCard {
        input.removeTextChangedListener(textWatcher)
        return SaveAndRestoreCard(card, listOf(deletePressed, resetPressed, input.text.toString()))
    }

    override fun restore(extras: List<Serializable>) {
        super.restore(extras)
        this.deletePressed = extras[0] as Boolean
        this.resetPressed = extras[1] as Boolean
        input.setText(extras[2] as String)

        if (deletePressed) {
            saveButton.visibility = View.GONE
            resetButton.visibility = View.GONE
            deleteButton.setText(R.string.confirm_delete_card)
        } else if (resetPressed) {
            resetButton.setText(R.string.confirm_reset_days)
            deleteButton.visibility = View.GONE
            saveButton.visibility = View.GONE
        }
    }
}