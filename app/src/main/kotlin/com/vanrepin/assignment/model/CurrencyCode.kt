package com.vanrepin.assignment.model

import com.vanrepin.assignment.R
import com.vanrepin.assignment.exceptions.InvalidCurrencyCodeException

inline class CurrencyCode(val value: String) {

    val nameStringId: Int
        get() = currencyNames.getOrPut(this) {
            getId<R.string>(value) ?: throw InvalidCurrencyCodeException(this)
        }

    val flagResourceId: Int
        get() = currencyFlags.getOrPut(this) {
            getId<R.drawable>(getValidResourceName(value)) ?: throw InvalidCurrencyCodeException(this)
        }

    companion object {
        val defaultCurrency = CurrencyCode("EUR")
    }
}

private inline fun <reified T> getId(name: String): Int? =
    T::class.members.find {
        it.name == name
    }?.let {
        it.call() as Int
    }

// reserved word
private val resourcesNames: Map<String, String> = mapOf("try" to "_try")

private fun getValidResourceName(value: String): String {
    val lowerCurrency = value.toLowerCase()
    return resourcesNames.getOrElse(lowerCurrency) { lowerCurrency }
}

private val currencyNames: MutableMap<CurrencyCode, Int> = mutableMapOf()
private val currencyFlags: MutableMap<CurrencyCode, Int> = mutableMapOf()
