package com.vanrepin.assignment.model.impl

import com.vanrepin.assignment.exceptions.InvalidCurrencyCodeException
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.RatesChangedListener
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.text.DecimalFormat

class CurrencyRegistryImplTest {

    private lateinit var out: CurrencyRegistryImpl

    private val TEST_CODE:CurrencyCode = CurrencyCode("USD")

    @Before
    fun setUp() {
        out = CurrencyRegistryImpl()
        out.update(
            mapOf(
                TEST_CODE to BigDecimal(2),
                CurrencyCode.defaultCurrency to BigDecimal.ONE
            )
        )
    }

    @Test
    fun setBaseCurrency_should_notify_listener() {
        val code = TEST_CODE
        out.baseCurrencyChangeListener = {
            assertEquals(code, it)
        }
        out.baseCurrency = code
    }

    @Test
    fun setBaseCurrency_should_change_it() {
        out.baseCurrency = TEST_CODE
        assertEquals(TEST_CODE, out.baseCurrency)
    }

    @Test
    fun setBaseCurrency_should_not_notify_when_same_currency() {
        var result = 0
        out.baseCurrencyChangeListener = {
            result++
        }
        out.baseCurrency = out.baseCurrency
        assertEquals(0, result)
    }

    @Test
    fun getBaseCurrency_should_return_default_value() {
        assertEquals(CurrencyCode.defaultCurrency, out.baseCurrency)
    }

    @Test
    fun setBaseValue_should_notify_listeners() {

        lateinit var result:String

        out.addRatesChangeListener(object :RatesChangedListener {
            override val currencyCode: CurrencyCode
                get() = TEST_CODE

            override fun onRatesChanged(formattedValue: String) {
                result = formattedValue
            }

        })
        out.setBaseValue( BigDecimal("10"))

        assertEquals(DecimalFormat().apply {
            maximumFractionDigits = 2
            minimumFractionDigits = 2
        }.format(BigDecimal(20)), result)
    }

    @Test
    fun setBaseValue_should_not_notify_listeners_when_needed() {

        var result = 0

        out.addRatesChangeListener(object :RatesChangedListener {
            override val currencyCode: CurrencyCode
                get() = TEST_CODE

            override fun onRatesChanged(formattedValue: String) {
                result++
            }

        })
        out.setBaseValue( BigDecimal("10"), false)

        assertEquals(0, result)
    }

    @Test
    fun addRatesChangeListener_should_only_notify_once() {

        var result = 0
        val listener = object :RatesChangedListener {
            override val currencyCode: CurrencyCode
                get() = TEST_CODE

            override fun onRatesChanged(formattedValue: String) {
                result++
            }

        }
        out.addRatesChangeListener(listener)
        out.addRatesChangeListener(listener)
        out.addRatesChangeListener(listener)
        out.addRatesChangeListener(listener)
        out.addRatesChangeListener(listener)
        out.addRatesChangeListener(listener)
        out.setBaseValue(BigDecimal("10"))

        assertEquals(1, result)
    }

    @Test
    fun update_should_notify_listeners() {

        var result = 0
        val listener = object :RatesChangedListener {
            override val currencyCode: CurrencyCode
                get() = TEST_CODE

            override fun onRatesChanged(formattedValue: String) {
                result++
            }

        }
        out.addRatesChangeListener(listener)
        out.update(mapOf(
            TEST_CODE to BigDecimal(2),
            CurrencyCode.defaultCurrency to BigDecimal.ONE
        ))

        assertEquals(1, result)
    }

    @Test(expected = InvalidCurrencyCodeException::class)
    fun getFormattedValue_should_throw_error_when_wrong_currency() {

        var result = 0
        val listener = object :RatesChangedListener {
            override val currencyCode: CurrencyCode
                get() = TEST_CODE

            override fun onRatesChanged(formattedValue: String) {
                result++
            }

        }
        out.addRatesChangeListener(listener)

        out.getFormattedValue(CurrencyCode("WTF"))
        assertEquals(0, result)
    }
}