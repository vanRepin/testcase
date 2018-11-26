package com.vanrepin.repinassignment.data

import android.databinding.ObservableField
import java.math.BigDecimal

open class BaseCurrencyItem {

    var value:ObservableField<BigDecimal> = ObservableField(defaultValue)

    @Volatile
    var currency:ObservableField<String> = ObservableField(defaultCurrency)

    companion object {
        const val defaultCurrency = "EUR"
        val defaultValue = BigDecimal(100)
    }
}