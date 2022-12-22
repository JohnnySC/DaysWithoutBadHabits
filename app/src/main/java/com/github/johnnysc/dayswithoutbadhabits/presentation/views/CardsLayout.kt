package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.children
import com.github.johnnysc.dayswithoutbadhabits.domain.Card
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardActions
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardsContainer
import com.github.johnnysc.dayswithoutbadhabits.presentation.MakeUi
import java.io.Serializable

/**
 * @author Asatryan on 18.12.2022
 */
class CardsLayout : LinearLayout, CardsContainer<AbstractCardView> {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun add(index: Int, view: AbstractCardView) = addView(view, index)

    override fun add(view: AbstractCardView) = addView(view)

    override fun replace(index: Int, view: AbstractCardView) {
        remove(index)
        add(index, view)
    }

    override fun remove(index: Int) {
        val child = getChildAt(index)
        (child as AbstractCardView).clear()
        removeView(child)
    }

    override fun position(cardUi: AbstractCardView) = indexOfChild(cardUi)

    fun restore(savedInstanceState: Bundle?, makeUi: MakeUi.Base, cardActions: CardActions) =
        savedInstanceState?.let { bundle ->
            val data = bundle.getSerializable(KEY) as SaveAndRestoreCardsLayout
            data.items.forEach { saveAndRestoreCard ->
                val view = saveAndRestoreCard.make(makeUi)
                saveAndRestoreCard.init(view, this, cardActions)
                add(view)
            }
        }

    fun save(bundle: Bundle) {
        val items = mutableListOf<SaveAndRestoreCard>()
        children.forEach {
            items.add((it as AbstractCardView).save())
        }
        bundle.putSerializable(KEY, SaveAndRestoreCardsLayout(items))
    }

    companion object {
        private const val KEY = "cardsLayout"
    }
}

private data class SaveAndRestoreCardsLayout(val items: List<SaveAndRestoreCard>) : Serializable

data class SaveAndRestoreCard(
    private val card: Card,
    private val extras: List<Serializable> = emptyList()
) : Card {

    override fun <T> make(makeUi: MakeUi<T>): T = card.make(makeUi)

    fun init(view: AbstractCardView, positionCallback: PositionCallback, cardActions: CardActions) =
        with(view) {
            init(positionCallback, cardActions)
            restore(extras)
        }
}