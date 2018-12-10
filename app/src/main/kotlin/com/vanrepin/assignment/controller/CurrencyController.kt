package com.vanrepin.assignment.controller

import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyResourcesHelper
import com.vanrepin.assignment.view.currency.CurrencyView

interface CurrencyController {

    val view: CurrencyView

    fun update(currency: CurrencyCode) {
        view.imageView.setImageResource(currencyResourcesHelper.getCurrencyFlagResourceId(currency))
        view.currencyCodeText.text = currency.value
        view.currencyNameText.setText(currencyResourcesHelper.getCurrencyNameStringId(currency))
    }

    companion object {
        internal lateinit var currencyResourcesHelper: CurrencyResourcesHelper
    }
}