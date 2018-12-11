package com.vanrepin.assignment.controller

import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.view.currency.CurrencyView

interface CurrencyController {

    val view: CurrencyView

    fun update(currency: CurrencyCode) {
        view.imageView.setImageResource(currency.flagResourceId)
        view.currencyCodeText.text = currency.value
        view.currencyNameText.setText(currency.nameStringId)
    }
}