package com.vanrepin.assignment.model

import java.math.BigDecimal

interface CurrencyRatesUpdater {

    var baseCurrencyChangeListener: (CurrencyCode) -> Unit

    fun update(rates: Map<CurrencyCode, BigDecimal>)
}