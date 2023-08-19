package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.github.johnnysc.dayswithoutbadhabits.R
import com.github.johnnysc.dayswithoutbadhabits.core.SimpleAnimator
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardActions
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardUi
import java.io.Serializable

/**
 * @author Asatryan on 18.12.2022
 */
abstract class AbstractCardView : FrameLayout, CardUi {

    abstract class AbleToMove : AbstractCardView {
        override fun canBeMoved() = true

        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        )

        abstract class Editable : AbleToMove {
            private lateinit var upButton: View
            private lateinit var downButton: View

            constructor(context: Context) : super(context)
            constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
            constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
                context,
                attrs,
                defStyleAttr
            )

            protected fun setUpNavigationButtons() {
                upButton = findViewById(R.id.upButton)
                downButton = findViewById(R.id.downButton)
                upButton.setOnClickListener {
                    actions.moveCardUp(positionCallback.position(this))
                    positionCallback.moveUp(positionCallback.position(this))
                }
                downButton.setOnClickListener {
                    actions.moveCardDown(positionCallback.position(this))
                    positionCallback.moveDown(positionCallback.position(this))
                }
            }

            override fun hideCanBeMoved() {
                upButton.visibility = View.GONE
                downButton.visibility = View.GONE
            }

            override fun showCanBeMovedDown() {
                downButton.visibility = View.VISIBLE
            }

            override fun showCanBeMovedUp() {
                upButton.visibility = View.VISIBLE
            }
        }
    }

    abstract class NonMoving : AbstractCardView {
        override fun canBeMoved() = false

        constructor(context: Context) : super(context)
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        )
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    protected var positionCallback: PositionCallback = PositionCallback.Empty()
    protected var actions: CardActions = CardActions.Empty()

    override fun init(positionCallback: PositionCallback, actions: CardActions) {
        this.actions = actions
        this.positionCallback = positionCallback
    }

    override fun clear() {
        positionCallback = PositionCallback.Empty()
        actions = CardActions.Empty()
    }

    open fun restore(extras: List<Serializable>) = Unit

    protected fun closeAnimation(action: () -> Unit) {
        val anim = ValueAnimator.ofInt(measuredHeight, 0)
        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams: ViewGroup.LayoutParams = layoutParams
            layoutParams.height = value
            setLayoutParams(layoutParams)
        }
        anim.duration = ANIMATION_DURATION
        anim.addListener(object : SimpleAnimator() {
            override fun onAnimationEnd(animation: Animator) = action.invoke()
        })
        anim.start()
    }

    protected fun hideAnimation(action: () -> Unit) {
        animate().apply {
            interpolator = LinearInterpolator()
            duration = ANIMATION_DURATION
            alpha(0f)
            setListener(object : SimpleAnimator() {
                override fun onAnimationEnd(animation: Animator) = action.invoke()
            })
            start()
        }
    }

    protected fun animateStart() {
        alpha = 0.1f
        animate().apply {
            interpolator = LinearInterpolator()
            duration = ANIMATION_DURATION
            alpha(1f)
            start()
        }
    }

    abstract fun save(): SaveAndRestoreCard

    override fun hideCanBeMoved() = Unit

    override fun showCanBeMovedDown() = Unit

    override fun showCanBeMovedUp() = Unit

    companion object {
        private const val ANIMATION_DURATION = 300L
    }
}