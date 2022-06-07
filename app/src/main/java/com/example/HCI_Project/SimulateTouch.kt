package com.example.HCI_Project

import android.os.SystemClock
import android.view.MotionEvent
import android.view.View

object SimulateTouch {
    fun simulateTouchEvent(view: View, x: Float, y: Float) {
        val downTime = SystemClock.uptimeMillis()

        val eventTime = SystemClock.uptimeMillis() + 100

        val metaState = 0

        val motionEvent = MotionEvent.obtain(downTime, eventTime,

            MotionEvent.ACTION_DOWN, x, y, metaState)

        view.dispatchTouchEvent(motionEvent)

        val upEvent = MotionEvent.obtain(downTime + 1000, eventTime + 1000,

            MotionEvent.ACTION_UP, x,y, metaState)

        view.dispatchTouchEvent(upEvent)
    }
}