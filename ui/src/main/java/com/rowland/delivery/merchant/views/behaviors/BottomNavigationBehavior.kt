package com.rowland.delivery.merchant.views.behaviors

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams
import androidx.core.view.ViewCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar.SnackbarLayout
import kotlin.math.max
import kotlin.math.min

/**
 * Created by rowlandoti on 08/03/2021
 *
 */
class BottomNavigationBehavior(@NonNull context: Context?, @NonNull attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<BottomNavigationView?>(context, attrs) {

    override fun onStartNestedScroll(
        @NonNull coordinatorLayout: CoordinatorLayout,
        @NonNull child: BottomNavigationView,
        @NonNull directTargetChild: View,
        @NonNull target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
        @NonNull coordinatorLayout: CoordinatorLayout,
        @NonNull child: BottomNavigationView,
        @NonNull target: View,
        dx: Int,
        dy: Int,
        @NonNull consumed: IntArray,
        type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = max(0.0f, min(child.height.toFloat(), child.translationY + dy))
    }

    override fun layoutDependsOn(
        @Nullable parent: CoordinatorLayout,
        child: BottomNavigationView,
        @Nullable dependency: View
    ): Boolean {
        if (dependency is SnackbarLayout) {
            this.updateSnackbar(child, dependency)
        }
        return super.layoutDependsOn(parent, child, dependency)
    }

    private fun updateSnackbar(child: BottomNavigationView, snackbarLayout: SnackbarLayout) {
        if (snackbarLayout.layoutParams is LayoutParams) {
            val layoutParams = snackbarLayout.layoutParams
                ?: throw RuntimeException(
                    "null cannot be cast to non-null type android.support.design." +
                            "widget.CoordinatorLayout.LayoutParams"
                )
            val params = layoutParams as LayoutParams
            params.anchorId = child.id
            params.anchorGravity = Gravity.TOP
            params.gravity = Gravity.TOP
            snackbarLayout.layoutParams = params
        }
    }
}