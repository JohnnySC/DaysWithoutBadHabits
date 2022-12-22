package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable

/**
 * @author Asatryan on 19.12.2022
 */
class ZeroDaysEditCardView : AbstractCardView {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var deletePressed = false
    private lateinit var card: Card
    private lateinit var input: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private var text = ""
    private val textChangeListener = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            saveButton.isEnabled = text != s.toString()
        }
    }

    fun setUp(id: Long, text: String, card: Card.ZeroDaysEdit) {
        this.text = text
        this.card = card
        inflate(context, R.layout.zero_days_edit_card_view, this)
        deleteButton = findViewById(R.id.zeroDaysEditDeleteButton)
        saveButton = findViewById(R.id.zeroDaysEditSaveButton)
        val cancelButton = findViewById<Button>(R.id.zeroDaysEditCancelButton)
        input = (findViewById<LinearLayout>(R.id.zeroDaysEditLinearLayout)
            .getChildAt(0) as TextInputEditText)

        input.addTextChangedListener(textChangeListener)
        input.setText(text)

        deleteButton.setOnClickListener {
            if (deletePressed)
                closeAnimation {
                    actions.deleteCard(positionCallback.position(this@ZeroDaysEditCardView), id)
                }
            else {
                saveButton.visibility = View.GONE
                deleteButton.setText(R.string.confirm_delete_card)
                deletePressed = true
            }
        }

        saveButton.setOnClickListener {
            hideAnimation {
                actions.saveEditedZeroDaysCard(
                    input.text.toString(),
                    positionCallback.position(this),
                    id
                )
            }
        }

        cancelButton.setOnClickListener {
            if (deletePressed) {
                deletePressed = false
                deleteButton.setText(R.string.delete_card)
                saveButton.visibility = View.VISIBLE
            } else
                hideAnimation {
                    actions.cancelEditZeroDaysCard(positionCallback.position(this), card)
                }
        }
        animateStart()
    }

    override fun save(): SaveAndRestoreCard {
        input.removeTextChangedListener(textChangeListener)
        return SaveAndRestoreCard(card, listOf(deletePressed, input.text.toString()))
    }

    override fun restore(extras: List<Serializable>) {
        super.restore(extras)
        deletePressed = extras[0] as Boolean
        input.setText(extras[1] as String)
        if (deletePressed) {
            saveButton.visibility = View.GONE
            deleteButton.setText(R.string.confirm_delete_card)
        }
    }
}