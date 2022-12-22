package com.github.johnnysc.dayswithoutbadhabits

/**
 * @author Asatryan on 20.12.2022
 */
class MainPage {

    fun addCard(position: Int) = AddCard(position)
    fun makeCard(position: Int) = MakeCard(position)
    fun zeroDaysCard(position: Int) = ZeroDaysCard(position)
    fun zeroDaysEditCard(position: Int) = ZeroDaysEditCard(position)
    fun nonZeroDaysCard(position: Int) = NonZeroDaysCard(position)
    fun nonZeroDaysEditCard(position: Int) = NonZeroDaysEditCard(position)
}

class AddCard(position: Int) : AbstractCard(R.id.addCardView, position)

class MakeCard(position: Int) : AbstractCard(R.id.makeCardView, position) {
    private val inputId = R.id.makeCardInputEditText
    private val saveButtonId = R.id.makeCardSaveButton
    private val cancelButtonId = R.id.makeCardCancelButton

    fun inputView() = inputId.viewAt(position)
    fun saveButton() = saveButtonId.viewAt(position)
    fun cancelButton() = cancelButtonId.viewAt(position)
}

class ZeroDaysCard(position: Int) : AbstractCard(R.id.zeroDaysCardView, position) {
    private val daysId = R.id.zeroDaysTextView
    private val titleId = R.id.zeroDaysTitleTextView
    private val editButtonId = R.id.zeroDaysEditButton

    fun daysView() = daysId.viewAt(position)
    fun titleView() = titleId.viewAt(position)
    fun editButton() = editButtonId.viewAt(position)
}

class ZeroDaysEditCard(position: Int) : AbstractCard(R.id.zeroDaysEditCardView, position) {
    private val cancelButtonId = R.id.zeroDaysEditCancelButton
    private val deleteButton = R.id.zeroDaysEditDeleteButton
    private val linearLayout = R.id.zeroDaysEditLinearLayout
    private val saveButtonId = R.id.zeroDaysEditSaveButton

    fun cancelButton() = cancelButtonId.viewAt(position)
    fun deleteButton() = deleteButton.viewAt(position)
    fun inputView() = linearLayout.viewAt(position, 0)
    fun saveButton() = saveButtonId.viewAt(position)
}

class NonZeroDaysCard(position: Int) : AbstractCard(R.id.nonZeroDaysCardView, position) {
    private val daysId = R.id.nonZeroDaysTextView
    private val titleId = R.id.nonZeroDaysTitleTextView
    private val editButtonId = R.id.nonZeroDaysEditButton

    fun daysView() = daysId.viewAt(position)
    fun titleView() = titleId.viewAt(position)
    fun editButton() = editButtonId.viewAt(position)
}

class NonZeroDaysEditCard(position: Int) : AbstractCard(R.id.nonZeroDaysEditCardView, position) {
    private val cancelButtonId = R.id.nonZeroDaysEditCancelButton
    private val deleteButton = R.id.nonZeroDaysEditDeleteButton
    private val linearLayoutId = R.id.nonZeroDaysLinearLayout
    private val saveButtonId = R.id.nonZeroDaysEditSaveButton
    private val resetButtonId = R.id.nonZeroDaysEditResetButton
    private val daysId = R.id.nonZeroDaysEditTextView

    fun cancelButton() = cancelButtonId.viewAt(position)
    fun deleteButton() = deleteButton.viewAt(position)
    fun inputView() = linearLayoutId.viewAt(position, 1)
    fun saveButton() = saveButtonId.viewAt(position)

    fun daysView() = daysId.viewAt(position)
    fun resetButton() = resetButtonId.viewAt(position)
}