package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.widget.Button
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.SimpleTextWatcher
import com.google.android.material.textfield.TextInputEditText
import java.io.Serializable


/**
 * @author Asatryan on 18.12.2022
 */
class MakeCardView : AbstractCardView.NonMoving {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val input: TextInputEditText

    init {
        inflate(context, R.layout.make_card_view, this)

        input = findViewById(R.id.makeCardInputEditText)
        val saveButton = findViewById<Button>(R.id.makeCardSaveButton)
        val cancelButton = findViewById<Button>(R.id.makeCardCancelButton)

        cancelButton.setOnClickListener {
            closeAnimation {
                actions.cancelMakeCard(positionCallback.position(this@MakeCardView))
            }
        }
        saveButton.setOnClickListener {
            hideAnimation {
                actions.saveNewCard(input.text.toString(), positionCallback.position(this))
            }
        }

        input.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                saveButton.isEnabled = text.isNotEmpty()
            }
        })
        animateStart()
    }

    override fun save() = SaveAndRestoreCard(Card.Make, listOf(input.text.toString()))

    override fun restore(extras: List<Serializable>) {
        super.restore(extras)
        input.setText(extras[0] as String)
    }
}