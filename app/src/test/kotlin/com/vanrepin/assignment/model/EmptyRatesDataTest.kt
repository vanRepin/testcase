package com.vanrepin.assignment.model

import com.vanrepin.assignment.exceptions.EmptyDataException
import org.junit.Assert.assertEquals
import org.junit.Test

class EmptyRatesDataTest internal constructor() {

    private val out:RatesData = EmptyRatesData

    @Test(expected = EmptyDataException::class)
    fun getBaseCurrencyCode() {
        out.baseCurrencyCode
    }

    @Test(expected = EmptyDataException::class)
    fun getRates() {
        out.rates
    }

    @Test
    fun isSameBaseCurrency() {
        assertEquals(false, out.isSameBaseCurrency(CurrencyCode("WTF")))
    }
}