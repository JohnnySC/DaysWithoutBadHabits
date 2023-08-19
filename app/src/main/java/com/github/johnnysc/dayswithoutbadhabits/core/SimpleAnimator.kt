package com.github.johnnysc.dayswithoutbadhabits.core

import android.animation.Animator

/**
 * @author Asatryan on 20.12.2022
 */
abstract class SimpleAnimator : Animator.AnimatorListener {
    override fun onAnimationStart(animation: Animator) = Unit
    override fun onAnimationEnd(animation: Animator) = Unit
    override fun onAnimationCancel(animation: Animator) = Unit
    override fun onAnimationRepeat(animation: Animator) = Unit
}