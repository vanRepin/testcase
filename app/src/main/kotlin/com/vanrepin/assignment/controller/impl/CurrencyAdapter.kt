package com.vanrepin.assignment.controller.impl

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.vanrepin.assignment.controller.CurrencyController
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRegistry


class CurrencyAdapter(
    private val items: MutableList<CurrencyCode>,
    private val currencyRegistry: CurrencyRegistry,
    private val baseCurrencyController: CurrencyController,
    private val currencyViewHolderFactory: (Context, (Int) -> Unit) -> CurrencyViewHolder
) : RecyclerView.Adapter<CurrencyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        currencyViewHolderFactory(parent.context, ::swapBaseCurrency)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.controller.update(items[position])
    }

    private fun swapBaseCurrency(baseItemIndex: Int) {
        val selectedCurrency = items[baseItemIndex]
        val oldBaseCurrency = currencyRegistry.baseCurrency
        currencyRegistry.baseCurrency = selectedCurrency
        items.removeAt(baseItemIndex)
        items.add(0, oldBaseCurrency)
        notifyItemRemoved(baseItemIndex)
        notifyItemInserted(0)
        baseCurrencyController.update(selectedCurrency)
    }
}