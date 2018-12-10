package com.vanrepin.assignment.model

inline class CurrencyCode(val value:String) {

    companion object {
        val defaultCurrency = CurrencyCode("EUR")
    }
}