package com.vanrepin.assignment.model

import com.vanrepin.assignment.exceptions.EmptyDataException
import org.json.JSONObject
import java.math.BigDecimal


interface RatesData {
    val baseCurrencyCode: CurrencyCode
    val rates: Map<CurrencyCode, BigDecimal>

    fun isSameBaseCurrency(code: CurrencyCode): Boolean

    companion object {
        fun parse(json: JSONObject): RatesData = RatesDataImpl(json)
    }
}

object EmptyRatesData : RatesData {

    override val baseCurrencyCode: CurrencyCode
        get() = throw EmptyDataException()
    override val rates: Map<CurrencyCode, BigDecimal>
        get() = throw EmptyDataException()

    override fun isSameBaseCurrency(code: CurrencyCode): Boolean = false

}

private class RatesDataImpl(json: JSONObject) : RatesData {

    override val baseCurrencyCode: CurrencyCode = CurrencyCode(json.getString("base"))

    override val rates: Map<CurrencyCode, BigDecimal>

    init {
        val ratesJson = json.getJSONObject("rates")
        rates = mutableMapOf(baseCurrencyCode to BigDecimal.ONE)
        ratesJson.keys().forEach {
            rates[CurrencyCode(it)] = BigDecimal(ratesJson.getString(it))
        }
    }

    override fun isSameBaseCurrency(code: CurrencyCode): Boolean = code == baseCurrencyCode
}
