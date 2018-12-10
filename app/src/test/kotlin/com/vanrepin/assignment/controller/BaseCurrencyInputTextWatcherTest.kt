package com.vanrepin.assignment.controller

import android.text.Editable
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.vanrepin.assignment.controller.impl.input.BaseCurrencyInputTextWatcher
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class BaseCurrencyInputTextWatcherTest {

    private lateinit var out: BaseCurrencyInputTextWatcher

    @Test
    fun should_be_zero_when_error() {

        val str = mock<Editable> { on { toString() } doReturn "qwe" }
        lateinit var result: BigDecimal
        out = BaseCurrencyInputTextWatcher { result = it }
        out.afterTextChanged(str)
        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun should_be_zero_when_empty_string() {

        val str = mock<Editable> { on { toString() } doReturn "" }
        lateinit var result: BigDecimal
        out = BaseCurrencyInputTextWatcher { result = it }
        out.afterTextChanged(str)
        assertEquals(BigDecimal.ZERO, result)
    }

    @Test
    fun should_be_value_when_valid() {

        val str = mock<Editable> { on { toString() } doReturn "10" }
        lateinit var result: BigDecimal
        out = BaseCurrencyInputTextWatcher { result = it }
        out.afterTextChanged(str)
        assertEquals(BigDecimal("10"), result)
    }

    @Test
    fun should_not_call_on_others() {
        var result = 0
        out = BaseCurrencyInputTextWatcher { result++ }
        out.onTextChanged("10", 0, 0, 2)
        out.beforeTextChanged("10", 0, 0, 2)
        assertEquals(0, result)
    }
}