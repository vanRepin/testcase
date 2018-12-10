package com.vanrepin.assignment.model.impl

import com.vanrepin.assignment.exceptions.InvalidCurrencyCodeException
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyResourcesHelper
import kotlin.reflect.KClass

class CurrencyResourcesHelperImpl(
    /* R.string */
    private val rstringClass: KClass<*>,
    /* R.drawable */
    private val rdrawableClass: KClass<*>
) : CurrencyResourcesHelper {

    private val currencyNames: MutableMap<CurrencyCode, Int> = mutableMapOf()
    private val currencyFlags: MutableMap<CurrencyCode, Int> = mutableMapOf()

    override fun getCurrencyNameStringId(code: CurrencyCode): Int = currencyNames.getOrPut(code) {
        getId(rstringClass, code.value) ?: throw InvalidCurrencyCodeException(code)
    }

    override fun getCurrencyFlagResourceId(code: CurrencyCode): Int = currencyFlags.getOrPut(code) {
        getId(rdrawableClass, getValidResourceName(code.value)) ?: throw InvalidCurrencyCodeException(code)
    }

    private fun getId(type: KClass<*>, name: String): Int? =
        type.members.find {
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
}