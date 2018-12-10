package com.vanrepin.assignment.model

interface CurrencyResourcesHelper {
    fun getCurrencyNameStringId(code: CurrencyCode): Int
    fun getCurrencyFlagResourceId(code: CurrencyCode): Int
}