package com.vanrepin.assignment.controller

import android.text.Spannable
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.vanrepin.assignment.controller.impl.input.BaseCurrencyInputFilter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BaseCurrencyInputFilterTest {

    private lateinit var out: BaseCurrencyInputFilter

    @Before
    fun setUp() {
        out = BaseCurrencyInputFilter(10, BigDecimal("10000"))
    }

    @Test
    fun filter_should_return_null_when_in_limit_range() {
        val destString = mock<Spannable> {
            on { toString() } doReturn "11"
            on { length } doReturn 2
        }
        Assert.assertNull(out.filter("10", 0, 2, destString, 0, 2))
    }

    @Test
    fun filter_should_return_empty_String_when_out_of_limit_range() {
        val destString = mock<Spannable> {
            on { toString() } doReturn "11"
            on { length } doReturn 2
        }
        Assert.assertEquals(out.filter("1.000000000000000000", 0, 2, destString, 0, 2), "")
    }

    @Test
    fun filter_should_return_empty_String_when_out_maxValue_range() {
        val destString = mock<Spannable> {
            on { toString() } doReturn "11"
            on { length } doReturn 2
        }
        Assert.assertEquals(out.filter("10001", 0, 2, destString, 0, 2), "")
    }

    @Test
    fun filter_should_return_null_when_not_a_number() {
        val destString = mock<Spannable> {
            on { toString() } doReturn "11"
            on { length } doReturn 2
        }
        Assert.assertEquals(out.filter("abc", 0, 2, destString, 0, 2), null)
    }
}