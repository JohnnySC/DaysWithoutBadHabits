package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
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

    private var innerState: UiState = UiState.Initial

    private val changeState = object : ChangeUiState {
        override fun update(uiState: UiState) {
            innerState = uiState
        }
    }

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
            innerState.delete(deleteButton, resetButton, saveButton, changeState) {
                closeAnimation {
                    actions.deleteCard(positionCallback.position(this), id)
                }
            }
        }

        resetButton.setOnClickListener {
            innerState.reset(deleteButton, resetButton, saveButton, changeState) {
                hideAnimation {
                    actions.resetNonZeroDaysCard(positionCallback.position(this), card)
                }
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
            innerState.cancel(deleteButton, resetButton, saveButton, changeState) {
                hideAnimation {
                    actions.cancelEditNonZeroDaysCard(positionCallback.position(this), card)
                }
            }
        }
        animateStart()
    }

    override fun save(): SaveAndRestoreCard {
        input.removeTextChangedListener(textWatcher)
        return SaveAndRestoreCard(card, listOf(innerState, input.text.toString()))
    }

    override fun restore(extras: List<Serializable>) {
        super.restore(extras)
        innerState = extras[0] as UiState
        input.setText(extras[1] as String)
        innerState.restore(deleteButton, resetButton, saveButton)
    }
}

private interface ChangeUiState {

    fun update(uiState: UiState)
}

private interface UiState : Serializable {

    fun delete(
        deleteButton: Button,
        resetButton: Button,
        saveButton: Button,
        changeUiState: ChangeUiState,
        close: () -> Unit
    ) = Unit

    fun reset(
        deleteButton: Button,
        resetButton: Button,
        saveButton: Button,
        changeUiState: ChangeUiState,
        close: () -> Unit
    ) = Unit

    fun cancel(
        deleteButton: Button,
        resetButton: Button,
        saveButton: Button,
        changeUiState: ChangeUiState,
        close: () -> Unit
    )

    fun restore(
        deleteButton: Button,
        resetButton: Button,
        saveButton: Button
    ) = Unit

    object Initial : UiState {

        override fun delete(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) {
            saveButton.visibility = View.GONE
            resetButton.visibility = View.GONE
            deleteButton.setText(R.string.confirm_delete_card)
            changeUiState.update(ConfirmDelete)
        }

        override fun reset(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) {
            resetButton.setText(R.string.confirm_reset_days)
            deleteButton.visibility = View.GONE
            saveButton.visibility = View.GONE
            changeUiState.update(ConfirmReset)
        }

        override fun cancel(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) = close.invoke()
    }

    object ConfirmDelete : UiState {

        override fun delete(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) = close.invoke()

        override fun cancel(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) {
            deleteButton.setText(R.string.delete_card)
            saveButton.visibility = View.VISIBLE
            resetButton.visibility = View.VISIBLE
            changeUiState.update(Initial)
        }

        override fun restore(deleteButton: Button, resetButton: Button, saveButton: Button) {
            saveButton.visibility = View.GONE
            resetButton.visibility = View.GONE
            deleteButton.setText(R.string.confirm_delete_card)
        }
    }

    object ConfirmReset : UiState {

        override fun reset(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) = close.invoke()

        override fun cancel(
            deleteButton: Button,
            resetButton: Button,
            saveButton: Button,
            changeUiState: ChangeUiState,
            close: () -> Unit
        ) {
            resetButton.setText(R.string.reset_card)
            saveButton.visibility = View.VISIBLE
            deleteButton.visibility = View.VISIBLE
            changeUiState.update(Initial)
        }

        override fun restore(deleteButton: Button, resetButton: Button, saveButton: Button) {
            resetButton.setText(R.string.confirm_reset_days)
            deleteButton.visibility = View.GONE
            saveButton.visibility = View.GONE
        }
    }
}