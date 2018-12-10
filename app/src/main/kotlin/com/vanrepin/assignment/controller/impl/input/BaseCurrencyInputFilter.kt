package com.vanrepin.assignment.controller.impl.input

import android.text.InputFilter
import android.text.Spanned
import java.math.BigDecimal

internal class BaseCurrencyInputFilter(private val maxLength: Int, private val maxValue: BigDecimal) :
    InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (source.isEmpty()) return null
        if (source.length + dest.length > maxLength) return ""
        val destString = dest.toString()
        val newVal = try {
            BigDecimal(
                destString.substring(0, dstart) + source.toString()
                        + destString.substring(dend, destString.length)
            )
        } catch (e: java.lang.NumberFormatException) {
            return null
        }

        if (newVal > maxValue) {
            return ""
        }
        return null
    }

}