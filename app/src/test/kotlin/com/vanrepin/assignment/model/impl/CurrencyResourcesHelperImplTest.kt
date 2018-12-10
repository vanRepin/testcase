package com.vanrepin.assignment.model.impl

import com.vanrepin.assignment.R
import com.vanrepin.assignment.model.CurrencyCode
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CurrencyResourcesHelperImplTest {

    private lateinit var currencyResourcesHelperImpl: CurrencyResourcesHelperImpl

    @Before
    fun setUp() {
        currencyResourcesHelperImpl = CurrencyResourcesHelperImpl(R.string::class, R.drawable::class)
    }


    @Test
    fun getCurrencyNameStringId() {
        assertEquals(R.string.EUR, currencyResourcesHelperImpl.getCurrencyNameStringId(CurrencyCode("EUR")))
        assertEquals(R.string.USD, currencyResourcesHelperImpl.getCurrencyNameStringId(CurrencyCode("USD")))
    }

    @Test
    fun getCurrencyFlagResourceId() {
        assertEquals(R.drawable.eur, currencyResourcesHelperImpl.getCurrencyFlagResourceId(CurrencyCode("EUR")))
        assertEquals(R.drawable._try, currencyResourcesHelperImpl.getCurrencyFlagResourceId(CurrencyCode("TRY")))
    }
}