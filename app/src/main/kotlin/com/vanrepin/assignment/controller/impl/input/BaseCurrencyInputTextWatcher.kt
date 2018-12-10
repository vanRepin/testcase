package com.vanrepin.assignment.controller.impl.input

import android.text.Editable
import android.text.TextWatcher
import java.math.BigDecimal

class BaseCurrencyInputTextWatcher(private val onChange: (BigDecimal) -> Unit) : TextWatcher {


    override fun afterTextChanged(s: Editable?) {
        s ?: return
        val str = s.toString()
        onChange(
            try {
                if (str.isEmpty()) BigDecimal.ZERO else BigDecimal(str)
            } catch (e: NumberFormatException) {
                BigDecimal.ZERO
            }
        )
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}