package com.github.johnnysc.dayswithoutbadhabits

/**
 * @author Asatryan on 20.12.2022
 */
class MainPage : AbstractPage() {

    fun addCard(position: Int) = AddCard(position)
    fun makeCard(position: Int) = MakeCard(position)
    fun zeroDaysCard(position: Int) = ZeroDaysCard(position)
    fun zeroDaysEditCard(position: Int) = ZeroDaysEditCard(position)
    fun nonZeroDaysCard(position: Int) = NonZeroDaysCard(position)
    fun nonZeroDaysEditCard(position: Int) = NonZeroDaysEditCard(position)
}

class AddCard(position: Int) : AbstractCard(R.id.addCardView, position)

class MakeCard(position: Int) : AbstractCard(R.id.makeCardView, position) {
    private val inputId = R.id.inputEditText
    private val saveButtonId = R.id.saveButton
    private val cancelButtonId = R.id.cancelButton

    fun inputView() = inputId.viewAt(position)
    fun saveButton() = saveButtonId.viewAt(position)
    fun cancelButton() = cancelButtonId.viewAt(position)
}

abstract class SomeDaysCard(id: Int, position: Int) : AbstractCard(id, position) {
    private val daysId = R.id.daysTextView
    private val titleId = R.id.titleTextView
    private val editButtonId = R.id.editButton

    fun daysView() = daysId.viewAt(position)
    fun titleView() = titleId.viewAt(position)
    fun editButton() = editButtonId.viewAt(position)
}

class ZeroDaysCard(position: Int) : SomeDaysCard(R.id.zeroDaysCardView, position)

abstract class SomeDaysEditCard(id: Int, position: Int) : AbstractCard(id, position) {
    private val cancelButtonId = R.id.cancelButton
    private val deleteButton = R.id.deleteButton
    private val inputViewId = R.id.inputEditText
    private val saveButtonId = R.id.saveButton

    fun cancelButton() = cancelButtonId.viewAt(position)
    fun deleteButton() = deleteButton.viewAt(position)
    fun inputView() = inputViewId.viewAt(position)
    fun saveButton() = saveButtonId.viewAt(position)
}

class ZeroDaysEditCard(position: Int) : SomeDaysEditCard(R.id.zeroDaysEditCardView, position)

class NonZeroDaysCard(position: Int) : SomeDaysCard(R.id.nonZeroDaysCardView, position)

class NonZeroDaysEditCard(position: Int) :
    SomeDaysEditCard(R.id.nonZeroDaysEditCardView, position) {
    private val resetButtonId = R.id.resetButton
    private val daysId = R.id.daysTextView

    fun daysView() = daysId.viewAt(position)
    fun resetButton() = resetButtonId.viewAt(position)
}