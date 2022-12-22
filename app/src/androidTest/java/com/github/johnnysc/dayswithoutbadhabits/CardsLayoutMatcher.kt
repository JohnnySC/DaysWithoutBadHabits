package com.github.johnnysc.dayswithoutbadhabits

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.github.johnnysc.dayswithoutbadhabits.presentation.views.CardsLayout
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * @author Asatryan on 20.12.2022
 */
class CardsLayoutMatcher(private val cardsLayoutId: Int) {

    fun atPositionWithChild(position: Int, layoutId: Int, childPosition: Int) =
        object : TypeSafeMatcher<View>() {
            var resources: Resources? = null
            var childView: View? = null

            override fun describeTo(description: Description) {
                var idDescription = cardsLayoutId.toString()
                if (this.resources != null) {
                    idDescription = try {
                        this.resources!!.getResourceName(cardsLayoutId)
                    } catch (e: Resources.NotFoundException) {
                        String.format("%s (resource name not found)", cardsLayoutId)
                    }
                }

                description.appendText("CardsLayout with id: $idDescription at position: $position")
            }

            override fun matchesSafely(item: View): Boolean {
                this.resources = item.resources

                if (childView == null) {
                    val cardsLayout = item.rootView.findViewById(cardsLayoutId) as CardsLayout
                    if (cardsLayout.id == cardsLayoutId) {
                        val abstractCardView = cardsLayout.getChildAt(position)
                        if (abstractCardView != null) {
                            childView = abstractCardView
                        }
                    } else {
                        return false
                    }
                }

                val targetView =
                    childView!!.findViewById<ViewGroup>(layoutId).getChildAt(childPosition)
                return item === targetView
            }

        }

    fun atPosition(position: Int, targetViewId: Int = -1) = object : TypeSafeMatcher<View>() {
        var resources: Resources? = null
        var childView: View? = null

        override fun describeTo(description: Description) {
            var idDescription = cardsLayoutId.toString()
            if (this.resources != null) {
                idDescription = try {
                    this.resources!!.getResourceName(cardsLayoutId)
                } catch (e: Resources.NotFoundException) {
                    String.format("%s (resource name not found)", cardsLayoutId)
                }
            }

            description.appendText("CardsLayout with id: $idDescription at position: $position")
        }

        override fun matchesSafely(item: View): Boolean {
            this.resources = item.resources

            if (childView == null) {
                val cardsLayout = item.rootView.findViewById(cardsLayoutId) as CardsLayout
                if (cardsLayout.id == cardsLayoutId) {
                    val abstractCardView = cardsLayout.getChildAt(position)
                    if (abstractCardView != null) {
                        childView = abstractCardView
                    }
                } else {
                    return false
                }
            }

            return if (targetViewId == -1) {
                item === childView
            } else {
                val targetView = childView!!.findViewById<View>(targetViewId)
                item === targetView
            }
        }
    }
}