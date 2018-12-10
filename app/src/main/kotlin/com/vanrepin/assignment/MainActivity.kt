package com.vanrepin.assignment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.network.RatesFetcher
import com.vanrepin.assignment.view.MainView
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    private lateinit var ratesFetcher: RatesFetcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainView = MainView()
        mainView.setContentView(this@MainActivity)
        val baseCurrencyController =
            buildBaseCurrencyController(mainView.baseCurrencyView, this)
        baseCurrencyController.update(CurrencyCode.defaultCurrency)
        ratesFetcher = buildRatesFetcher()
        ratesFetcher.getCurrencies { currencies ->
            mainView.setAdapter(buildCurrencyAdapter(currencies, baseCurrencyController))
        }
    }

    override fun onResume() {
        super.onResume()
        ratesFetcher.startLoop()
    }

    override fun onPause() {
        super.onPause()
        ratesFetcher.stopLoop()
    }
}
