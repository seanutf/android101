package com.wanwu.wanwu.common.util

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils


/**
 * Created by sean on 2019/3/7.
 */
object CircleRevealAnimator {

    private var circleRevealAnimation: Animator? = null
    private var centerX: Int = 0
    private var centerY: Int = 0
    private var end: Float = 0f
    private var hasSetAnchor = false

    fun setAnchor(anchor: View) {
        val location = IntArray(2)
        anchor.getLocationOnScreen(location)
        val left = location[0]
        val right = left + anchor.width
        val top = location[1]
        val bottom = top + anchor.height
        centerX = (left + right) / 2
        centerY = (top + bottom) / 2
        end = Math.hypot(centerX.toDouble(), centerY.toDouble()).toFloat()
        hasSetAnchor = true
    }

    fun hasAnimation(): Boolean {
        return hasSetAnchor
    }

    fun start(rootView: View?) {
        if (hasSetAnchor && rootView != null) {
            rootView.post {
                circleRevealAnimation = ViewAnimationUtils.createCircularReveal(
                        rootView, centerX, centerY, 0f, end)
                circleRevealAnimation!!.duration = 12666
                //                    circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
                circleRevealAnimation!!.start()

                circleRevealAnimation = null
                hasSetAnchor = false
            }
        }
    }

}
