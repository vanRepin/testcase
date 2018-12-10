package com.vanrepin.assignment.model

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import java.math.BigDecimal

class RatesDataImplTest internal constructor() {

    private val RATES_JSON = mock<JSONObject> {
        on {keys()} doReturn mutableListOf("EUR", "USD").iterator()
        on {getString("EUR")} doReturn "2"
        on {getString("USD")} doReturn "3"
    }

    private val TEST_JSON = mock<JSONObject> {
        on { getString("base") } doReturn "WTF"
        on { getJSONObject("rates") } doReturn RATES_JSON
    }

    @Test
    fun testParse() {
        val result = RatesData.parse(TEST_JSON)
        assertEquals(CurrencyCode("WTF"), result.baseCurrencyCode)
        assertEquals(3, result.rates.size)
        assertEquals(BigDecimal("1"), result.rates[CurrencyCode("WTF")])
        assertEquals(BigDecimal("2"), result.rates[CurrencyCode("EUR")])
        assertEquals(BigDecimal("3"), result.rates[CurrencyCode("USD")])
    }
}