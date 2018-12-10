package com.vanrepin.assignment

import android.content.Context
import com.vanrepin.assignment.controller.CurrencyController
import com.vanrepin.assignment.controller.impl.BaseCurrencyController
import com.vanrepin.assignment.controller.impl.CurrencyAdapter
import com.vanrepin.assignment.controller.impl.CurrencyViewHolder
import com.vanrepin.assignment.controller.impl.DependantCurrencyController
import com.vanrepin.assignment.controller.impl.input.BaseCurrencyInputTextWatcher
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.RatesData
import com.vanrepin.assignment.model.impl.CurrencyRegistryImpl
import com.vanrepin.assignment.model.impl.CurrencyResourcesHelperImpl
import com.vanrepin.assignment.network.RatesFetcher
import com.vanrepin.assignment.view.InputHelperImpl
import com.vanrepin.assignment.view.currency.CurrencyView
import com.vanrepin.assignment.view.currency.CurrencyViewImpl
import org.json.JSONObject
import java.net.URL

fun buildBaseCurrencyController(
    baseCurrencyView: CurrencyView,
    context: Context
): CurrencyController {
    CurrencyController.currencyResourcesHelper = CurrencyResourcesHelperImpl(R.string::class, R.drawable::class)
    return BaseCurrencyController(
        baseCurrencyView,
        currencyRegistry,
        InputHelperImpl(context),
        BaseCurrencyInputTextWatcher { currencyRegistry.setBaseValue(it) }
    )
}

fun buildCurrencyAdapter(
    currencies: Set<CurrencyCode>,
    baseCurrencyController: CurrencyController
) = CurrencyAdapter(
    items = currencies.filter { it != currencyRegistry.baseCurrency }.toMutableList(),
    currencyRegistry = currencyRegistry,
    baseCurrencyController = baseCurrencyController,
    currencyViewHolderFactory = { context, onSelect ->
        CurrencyViewHolder(DependantCurrencyController(CurrencyViewImpl(), currencyRegistry), context, onSelect)
    }
)

fun buildRatesFetcher() = RatesFetcher(currencyRegistry) {
    RatesData.parse(JSONObject(URL(it).readText()))
}

private val currencyRegistry = CurrencyRegistryImpl()