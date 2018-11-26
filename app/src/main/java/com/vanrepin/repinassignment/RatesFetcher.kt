package com.vanrepin.repinassignment

import android.content.res.Resources
import android.databinding.ObservableArrayMap
import com.vanrepin.repinassignment.data.BaseCurrencyItem
import com.vanrepin.repinassignment.data.CurrencyItem
import kotlinx.coroutines.*
import org.json.JSONObject
import java.math.BigDecimal
import java.net.URL
import kotlin.coroutines.CoroutineContext
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

class RatesFetcher(
    private val currencies: ObservableArrayMap<String, CurrencyItem>,
    private val baseCurrency: BaseCurrencyItem,
    private val resources: Resources
) : CoroutineScope {

    private lateinit var job: Job
    private var fetchInterval = 1000

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    fun start() {
        job = Job()
        launch {
            startRatesFetchLoop()
        }
    }

    fun stop() {
        job.cancel()
    }

    private fun startRatesFetchLoop() = launch {
        var lastRequestTimeMillis = 0L
        while (true) {
            var json = EMPTY_JSON
            lastRequestTimeMillis = measureTimeMillis {
                json = fetchRates()
            }
            if (!isResponseValid(json)) {
                continue
            }
            fetchInterval = MIN_FETCH_INTERVAL
            val rates = json.getJSONObject("rates")
            rates.keys().forEach {
                currencies.getOrPut(it) { CurrencyItem(baseCurrency, it, resources) }.apply {
                    rate.set(BigDecimal(rates.getString(it)))
                }
            }
            delay(max(fetchInterval - lastRequestTimeMillis, 0))
        }
    }

    private fun isResponseValid(json: JSONObject): Boolean {

        return json.has("base") && json.getString("base") == baseCurrency.currency.get()
    }


    private suspend fun fetchRates() = async(Dispatchers.IO) {
        try {
            JSONObject(URL("https://revolut.duckdns.org/latest?base=${baseCurrency.currency.get()}").readText())
        } catch (e: Throwable) {
            fetchInterval = min(fetchInterval * 2, MAX_FETCH_INTERVAL)
            EMPTY_JSON
        }
    }.await()

    companion object {
        private const val MAX_FETCH_INTERVAL = 30_000
        private const val MIN_FETCH_INTERVAL = 1_000
        private val EMPTY_JSON = JSONObject()
    }
}
