package com.vanrepin.assignment.view

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

interface InputHelper {
    fun showKeyboard(input:View)
}

class InputHelperImpl(context:Context) : InputHelper {

    private val inputManager: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    override fun showKeyboard(input:View) {
        input.requestFocus()
        inputManager.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT)
    }
}