package com.vanrepin.assignment.network

import android.util.Log
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRatesUpdater
import com.vanrepin.assignment.model.EmptyRatesData
import com.vanrepin.assignment.model.RatesData
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureTimeMillis

class RatesFetcher(
    private val ratesUpdater: CurrencyRatesUpdater,
    private val urlRequestFactory: (String) -> RatesData
) : CoroutineScope {

    private lateinit var job: Job
    private var fetchInterval = 1000

    private var baseCurrencyCode: CurrencyCode = CurrencyCode.defaultCurrency

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun getCurrencies(onLoaded: (Set<CurrencyCode>) -> Unit) = launch(coroutineContext) {
        val result = fetchRates().rates
        ratesUpdater.update(result)
        launch(Dispatchers.Main) {
            onLoaded(result.keys)
        }
    }

    init {
        ratesUpdater.baseCurrencyChangeListener = {
            baseCurrencyCode = it
        }
    }

    fun startLoop() {
        job = startRatesFetchLoop()
    }

    fun stopLoop() {
        job.cancel()
    }

    private fun startRatesFetchLoop() = launch {
        var lastRequestTimeMillis: Long
        while (true) {
            var data: RatesData = EmptyRatesData

            lastRequestTimeMillis = measureTimeMillis {
                data = fetchRates()
            }
            if (!data.isSameBaseCurrency(baseCurrencyCode)) {
                continue
            }
            fetchInterval = MIN_FETCH_INTERVAL
            runBlocking(Dispatchers.Main) {
                ratesUpdater.update(data.rates)
            }

            delay(max(fetchInterval - lastRequestTimeMillis, 0))
        }
    }

    private fun fetchRates() = try {
        urlRequestFactory("https://revolut.duckdns.org/latest?base=${baseCurrencyCode.value}")
    } catch (e: Throwable) {
        Log.w("RatesFetcher.fetchRates", e)
        fetchInterval = min(
            fetchInterval * 2,
            MAX_FETCH_INTERVAL
        )
        EmptyRatesData
    }

    companion object {
        private const val MAX_FETCH_INTERVAL = 30_000
        private const val MIN_FETCH_INTERVAL = 1_000
    }
}
