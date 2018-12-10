package com.vanrepin.assignment.model

import java.math.BigDecimal

/**
 * Controllers should change mutable data and get formatted currency value via this class
 */
interface CurrencyRegistry {

    /**
     * this currency is used to request rates.
     * after change next request to rates will be made with new currency
     */
    var baseCurrency: CurrencyCode

    /**
     * amount of base currency to calculate other currencies
     * currencyValue = baseValue*currencyRate
     */
    val baseValue: BigDecimal

    fun setBaseValue(value:BigDecimal, notify:Boolean = true)

    /**
     * get String value of currency
     */
    fun getFormattedValue(currencyCode: CurrencyCode): String


    /**
     * listen to rates changes.
     * callback is called when new rates received from the net
     */
    fun addRatesChangeListener(listener: RatesChangedListener)
}

interface RatesChangedListener {
    val currencyCode: CurrencyCode
    fun onRatesChanged(formattedValue: String)
}