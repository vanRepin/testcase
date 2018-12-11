package com.vanrepin.assignment.model

import com.vanrepin.assignment.R
import junit.framework.Assert.assertEquals
import org.junit.Test

class CurrencyCodeTest {

    @Test
    fun getNameStringId() {
        assertEquals(R.string.EUR, CurrencyCode("EUR").nameStringId)
        assertEquals(R.string.USD, CurrencyCode("USD").nameStringId)
    }

    @Test
    fun getFlagResourceId() {
        assertEquals(R.drawable.eur, CurrencyCode("EUR").flagResourceId)
        assertEquals(R.drawable._try, CurrencyCode("TRY").flagResourceId)
    }
}