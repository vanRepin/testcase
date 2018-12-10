package com.vanrepin.assignment.controller.impl

import com.vanrepin.assignment.controller.CurrencyController
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRegistry
import com.vanrepin.assignment.model.RatesChangedListener
import com.vanrepin.assignment.view.currency.CurrencyView
import com.vanrepin.assignment.view.isNotZero

class DependantCurrencyController(
    override val view: CurrencyView,
    private val registry: CurrencyRegistry
) : CurrencyController, RatesChangedListener {

    override var currencyCode: CurrencyCode = CurrencyCode.defaultCurrency

    override fun update(currency: CurrencyCode) {
        super.update(currency)
        currencyCode = currency

        registry.addRatesChangeListener(this)

        onRatesChanged(registry.getFormattedValue(currencyCode))
    }

    override fun onRatesChanged(formattedValue: String) {
        view.currencyValueInput.apply {
            setText(formattedValue)
            isEnabled = formattedValue.isNotZero()
        }
    }
}