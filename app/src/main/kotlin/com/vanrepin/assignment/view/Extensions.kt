package com.vanrepin.assignment.view

import android.support.v7.widget.RecyclerView
import android.text.TextWatcher
import android.view.ViewManager
import android.widget.EditText
import com.makeramen.roundedimageview.RoundedImageView
import org.jetbrains.anko.custom.ankoView

fun ViewManager.roundedImageView(init: RoundedImageView.() -> Unit) = ankoView({ RoundedImageView(it) }, 0, init)

fun ViewManager.recycleView(init: RecyclerView.() -> Unit) = ankoView({ RecyclerView(it) }, 0, init)

fun String.isNotZero() = this != "0"

inline fun EditText.doWithoutNotification(textWatcher: TextWatcher, action:EditText.() -> Unit) {
    this.removeTextChangedListener(textWatcher)
    action(this)
    this.addTextChangedListener(textWatcher)
}
