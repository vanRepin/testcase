package com.vanrepin.assignment.model.impl

import com.vanrepin.assignment.exceptions.InvalidCurrencyCodeException
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRatesUpdater
import com.vanrepin.assignment.model.CurrencyRegistry
import com.vanrepin.assignment.model.RatesChangedListener
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


class CurrencyRegistryImpl : CurrencyRegistry, CurrencyRatesUpdater {

    override var baseCurrency: CurrencyCode = CurrencyCode.defaultCurrency
        set(value) {
            if (field == value) return
            field = value
            baseValue = getValue(value)
            baseCurrencyChangeListener(value)
        }

    override var baseCurrencyChangeListener: (CurrencyCode) -> Unit = {}

    private lateinit var rates: Map<CurrencyCode, BigDecimal>

    private val listeners: MutableSet<RatesChangedListener> =
        Collections.newSetFromMap(WeakHashMap<RatesChangedListener, Boolean>())

    override var baseValue: BigDecimal = BigDecimal("100")
        private set

    override fun setBaseValue(value: BigDecimal, notify: Boolean) {
        if (baseValue == value) return
        baseValue = value
        if (notify) notifyListeners()
    }

    override fun getFormattedValue(currencyCode: CurrencyCode): String =
        if (baseValue == BigDecimal.ZERO) "0"
        else formatDecimal(currencyCode, if (currencyCode == baseCurrency) baseValue else getValue(currencyCode))

    private fun getValue(currencyCode: CurrencyCode): BigDecimal =
        getRate(currencyCode).multiply(baseValue).setScale(getPrecision(currencyCode), RoundingMode.DOWN)

    override fun addRatesChangeListener(listener: RatesChangedListener) {
        listeners.add(listener)
    }

    private fun getRate(currencyCode: CurrencyCode) =
        rates[currencyCode] ?: throw InvalidCurrencyCodeException(currencyCode)

    override fun update(rates: Map<CurrencyCode, BigDecimal>) {
        this.rates = rates
        notifyListeners()
    }

    private fun notifyListeners() = listeners.forEach {
        it.onRatesChanged(getFormattedValue(it.currencyCode))
    }

    private val precisions: Map<CurrencyCode, Int> = mapOf(CurrencyCode("IDR") to 0)

    private val format: DecimalFormat = DecimalFormat().apply { isParseBigDecimal = true }

    private fun formatDecimal(currency: CurrencyCode, value: BigDecimal) = format.apply {
        maximumFractionDigits = getPrecision(currency)
        minimumFractionDigits = getPrecision(currency)
    }.format(value)

    private fun getPrecision(currency: CurrencyCode) = precisions.getOrElse(currency) { 2 }
}