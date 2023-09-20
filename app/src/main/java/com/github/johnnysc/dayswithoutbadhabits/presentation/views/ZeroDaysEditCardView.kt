package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
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
class ZeroDaysEditCardView : AbstractCardView.AbleToMove.Editable {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var innerState: State = State.Initial

    private val changeState = object : ChangeState {
        override fun update(state: State) {
            innerState = state
        }
    }

    private lateinit var card: Card
    private lateinit var input: TextInputEditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private var text = ""
    private val textChangeListener = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: android.text.Editable?) {
            saveButton.isEnabled = text != s.toString()
        }
    }

    fun setUp(id: Long, text: String, card: Card.ZeroDaysEdit) {
        this.text = text
        this.card = card
        inflate(context, R.layout.zero_days_edit_card_view, this)
        setUpNavigationButtons()
        deleteButton = findViewById(R.id.zeroDaysEditDeleteButton)
        saveButton = findViewById(R.id.zeroDaysEditSaveButton)
        val cancelButton = findViewById<Button>(R.id.zeroDaysEditCancelButton)
        input = (findViewById<LinearLayout>(R.id.zeroDaysEditLinearLayout)
            .getChildAt(0) as TextInputEditText)

        input.addTextChangedListener(textChangeListener)
        input.setText(text)

        deleteButton.setOnClickListener {
            innerState.delete(saveButton, deleteButton, changeState) {
                closeAnimation {
                    actions.deleteCard(positionCallback.position(this@ZeroDaysEditCardView), id)
                }
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
            innerState.cancel(saveButton, deleteButton, changeState) {
                hideAnimation {
                    actions.cancelEditZeroDaysCard(positionCallback.position(this), card)
                }
            }
        }
        animateStart()
    }

    override fun save(): SaveAndRestoreCard {
        input.removeTextChangedListener(textChangeListener)
        return SaveAndRestoreCard(card, listOf(innerState, input.text.toString()))
    }

    override fun restore(extras: List<Serializable>) {
        super.restore(extras)
        innerState = extras[0] as State
        input.setText(extras[1] as String)
        innerState.restore(saveButton, deleteButton)
    }
}

private interface State : Serializable {

    fun restore(saveButton: View, deleteButton: Button) = Unit

    fun cancel(saveButton: View, deleteButton: Button, changeState: ChangeState, close: () -> Unit)

    fun delete(saveButton: View, deleteButton: Button, changeState: ChangeState, close: () -> Unit)

    object Initial : State {

        override fun cancel(
            saveButton: View,
            deleteButton: Button,
            changeState: ChangeState,
            close: () -> Unit
        ) = close.invoke()

        override fun delete(
            saveButton: View,
            deleteButton: Button,
            changeState: ChangeState,
            close: () -> Unit
        ) {
            saveButton.visibility = View.GONE
            deleteButton.setText(R.string.confirm_delete_card)
            changeState.update(ConfirmDelete)
        }
    }

    object ConfirmDelete : State {

        override fun cancel(
            saveButton: View,
            deleteButton: Button,
            changeState: ChangeState,
            close: () -> Unit
        ) {
            deleteButton.setText(R.string.delete_card)
            saveButton.visibility = View.VISIBLE
            changeState.update(Initial)
        }

        override fun delete(
            saveButton: View,
            deleteButton: Button,
            changeState: ChangeState,
            close: () -> Unit
        ) = close.invoke()

        override fun restore(saveButton: View, deleteButton: Button) {
            saveButton.visibility = View.GONE
            deleteButton.setText(R.string.confirm_delete_card)
        }
    }
}

private interface ChangeState {

    fun update(state: State)
}