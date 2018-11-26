package com.vanrepin.repinassignment.data

import android.content.res.Resources
import android.databinding.Observable
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import com.vanrepin.repinassignment.R
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.reflect.KCallable
import kotlin.reflect.KClass

class CurrencyItem(
    private val baseCurrencyItem: BaseCurrencyItem,
    val currency: String,
    private val resources: Resources
) {
    var rate: ObservableField<BigDecimal> = ObservableField(BigDecimal("1.0"))
    var value: ObservableField<BigDecimal> = ObservableField()
    val currencyFlag: Drawable? by lazy {
        val prop = getProp<R.drawable>(getValidResourceName(currency))
        prop?.apply {
            return@lazy resources.getDrawable(prop.call() as Int)
        }
        return@lazy null
    }
    val currencyName: String by lazy {
        val prop = getProp<R.string>(currency)
        prop?.apply {
            return@lazy resources.getString(prop.call() as Int)
        }
        return@lazy currency
    }

    val isBaseCurrency: Boolean
        get() = currency == baseCurrencyItem.currency.get()

    init {
        updateValue()
        baseCurrencyItem.value.addOnPropertyChangedCallback(CurrencyChangeCallback())
        rate.addOnPropertyChangedCallback(CurrencyChangeCallback())
    }

    private fun updateValue() {
        val baseValue = baseCurrencyItem.value.get()!!
        if (baseValue == BigDecimal.ZERO) {
            value.set(BigDecimal.ZERO)
        } else {
            value.set(
                baseValue.multiply(rate.get()).setScale(
                    getPrecision(currency),
                    RoundingMode.DOWN
                )
            )
        }
    }

    private inner class CurrencyChangeCallback : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (!isBaseCurrency) {
                updateValue()
            }
        }
    }

    override fun toString(): String {
        return "currency: $currency rate:${rate.get()} value:${value.get()}"
    }

    companion object {

        private val precisions: Map<String, Int> = mapOf()

        private val resourcesNames: Map<String, String> = mapOf("try" to "_try")

        private fun getPrecision(currency: String) = precisions.getOrElse(currency) { 2 }

        private fun getValidResourceName(currency: String): String {
            val lowerCurrency = currency.toLowerCase()
            return resourcesNames.getOrElse(lowerCurrency) { lowerCurrency }
        }

        private inline fun <reified T> getProp(name:String):KCallable<*>? {
            return T::class.members.find {
                it.name == name
            }
        }
    }
}