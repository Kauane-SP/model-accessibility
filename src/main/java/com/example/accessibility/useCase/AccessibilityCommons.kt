package com.example.projetoacessibilidade.useCase

import android.content.Context
import android.content.Context.ACCESSIBILITY_SERVICE
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.view.accessibility.AccessibilityNodeInfo
import android.view.accessibility.AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS
import android.widget.ListView
import android.widget.RadioButton
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.example.accessibility.helper.Constants.DELAY_CONVERSATION


object AccessibilityCommons {

    fun addTitleSemantics(view: View) {
        ViewCompat.setAccessibilityDelegate(
            view,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.isHeading = true
                }
            }
        )
    }

    fun focusOnListView(listView: ListView, index: Int) {
        val container = listView.getChildAt(0) as ViewGroup
//        val focusView = container.getChildAt(index)
    }

    fun accessibilityRadioButton(view: View?) {
        view?.accessibilityDelegate = object : View.AccessibilityDelegate() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfo
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.className = RadioButton::class.java.name
            }
        }
    }

    fun accessibilityButton(view: View) {
        ViewCompat.setAccessibilityDelegate(view, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(host: View, info: AccessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.isClickable = true
                info.className = "android.widget.Button"
            }
        })
    }

    private fun eventSemanticsAccessibility(context: Context, event: AccessibilityEvent) {
        val manager = context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (accessibilityOn(context)) {
            manager.sendAccessibilityEvent(event)
        }
    }

    fun setViewContentDescription(view: View, description: String) {
        view.contentDescription = description
    }

    fun accessibilityOn(context: Context): Boolean {
        val type = context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        val isAccessibilityEnabled = type.isEnabled
        val isExploreByTouchEnabled = type.isTouchExplorationEnabled
        return isAccessibilityEnabled && isExploreByTouchEnabled
    }

    fun focusAccessibilityView(view: View) {
        view.postDelayed(
            { requestFocus(view) },
            DELAY_CONVERSATION.toLong()
        )
    }

    fun requestFocus(view: View) {
        view.performAccessibilityAction(ACTION_ACCESSIBILITY_FOCUS, null)
        view.requestFocus()
    }
}