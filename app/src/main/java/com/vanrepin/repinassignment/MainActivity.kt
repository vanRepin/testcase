package com.vanrepin.repinassignment

import android.databinding.ObservableArrayMap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.vanrepin.repinassignment.data.BaseCurrencyItem
import com.vanrepin.repinassignment.data.CurrencyAdapter
import com.vanrepin.repinassignment.data.CurrencyItem

class MainActivity : AppCompatActivity() {

    private val baseCurrencyItem = BaseCurrencyItem()
    private lateinit var currencies: ObservableArrayMap<String, CurrencyItem>

    private lateinit var ratesFetcher: RatesFetcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currencies = ObservableArrayMap<String, CurrencyItem>()
            .apply {
                val currency = baseCurrencyItem.currency.get()!!
                put(currency, CurrencyItem(baseCurrencyItem, currency, resources))
            }
        ratesFetcher = RatesFetcher(currencies, baseCurrencyItem, resources)

        setContentView(R.layout.activity_main)
        val recycleView = findViewById<RecyclerView>(R.id.view)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = CurrencyAdapter(currencies, baseCurrencyItem, recycleView)

    }

    override fun onResume() {
        super.onResume()
        ratesFetcher.start()
    }

    override fun onPause() {
        super.onPause()
        ratesFetcher.stop()
    }
}
