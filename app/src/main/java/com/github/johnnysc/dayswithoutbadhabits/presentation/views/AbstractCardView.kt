package com.github.johnnysc.dayswithoutbadhabits.presentation.views

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import com.github.johnnysc.dayswithoutbadhabits.core.SimpleAnimator
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardActions
import com.github.johnnysc.dayswithoutbadhabits.presentation.CardUi

/**
 * @author Asatryan on 18.12.2022
 */
abstract class AbstractCardView : FrameLayout, CardUi {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    protected var positionCallback: PositionCallback = PositionCallback.Empty()
    protected var actions: CardActions = CardActions.Empty()
    protected var animationEnabled: Boolean = true

    override fun init(
        positionCallback: PositionCallback,
        actions: CardActions,
        animationEnabled: Boolean
    ) {
        this.animationEnabled = animationEnabled
        this.actions = actions
        this.positionCallback = positionCallback
    }

    override fun clear() {
        positionCallback = PositionCallback.Empty()
        actions = CardActions.Empty()
    }

    protected fun closeAnimation(action: () -> Unit) {
        if (animationEnabled) {
            val anim = ValueAnimator.ofInt(measuredHeight, 0)
            anim.addUpdateListener { valueAnimator ->
                val value = valueAnimator.animatedValue as Int
                val layoutParams: ViewGroup.LayoutParams = layoutParams
                layoutParams.height = value
                setLayoutParams(layoutParams)
            }
            anim.duration = ANIMATION_DURATION
            anim.addListener(object : SimpleAnimator() {
                override fun onAnimationEnd(animation: Animator?) = action.invoke()
            })
            anim.start()
        } else action.invoke()
    }

    protected fun hideAnimation(action: () -> Unit) {
        if (animationEnabled) {
            animate().apply {
                interpolator = LinearInterpolator()
                duration = ANIMATION_DURATION
                alpha(0f)
                setListener(object : SimpleAnimator() {
                    override fun onAnimationEnd(animation: Animator?) = action.invoke()
                })
                start()
            }
        } else action.invoke()
    }

    protected fun animateStart() {
        if (animationEnabled) {
            alpha = 0.1f
            animate().apply {
                interpolator = LinearInterpolator()
                duration = ANIMATION_DURATION
                alpha(1f)
                start()
            }
        }
    }

    companion object {
        private const val ANIMATION_DURATION = 300L
    }
}