package com.vanrepin.repinassignment.data

import android.databinding.ObservableArrayMap
import android.databinding.ObservableMap
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import com.vanrepin.repinassignment.databinding.CurrencyItemViewBinding
import java.math.BigDecimal


class CurrencyAdapter(
    currencies: ObservableArrayMap<String, CurrencyItem>,
    private val baseCurrencyItem: BaseCurrencyItem,
    private val recycleView: RecyclerView
) : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    private val items: MutableList<CurrencyItem> = mutableListOf()

    init {
        currencies.addOnMapChangedCallback(OnMapChangedCallback())
    }

    inner class ViewHolder(private val binding: CurrencyItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        private val changeWatcher = ChangeWatcher()
        init {
            binding.root.setOnClickListener {
                binding.valueText.requestFocus()
            }
            binding.valueText.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    setBaseCurrency()
                    recycleView.smoothScrollToPosition(0)
                }
            }
        }

        fun bind(item: CurrencyItem) {
            binding.item = item
            binding.flag.setImageDrawable(item.currencyFlag)
            if (item.isBaseCurrency) {
                binding.valueText.addTextChangedListener(changeWatcher)
            } else {
                binding.valueText.removeTextChangedListener(changeWatcher)
            }
        }

        private fun setBaseCurrency() {
            val item = binding.item!!
            if (baseCurrencyItem.currency.get() == item.currency) return
            val currentValue = item.value.get()
            val itemIndex = adapterPosition
            item.rate.set(BigDecimal.ONE)
            baseCurrencyItem.value.set(currentValue)
            baseCurrencyItem.currency.set(item.currency)
            for (i in itemIndex downTo 1) {
                items[i] = items[i - 1]
            }
            items[0] = item

            notifyDataSetChanged()
        }

        private inner class ChangeWatcher:TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val value = s.toString()
                if (value.isEmpty()) {
                    baseCurrencyItem.value.set(BigDecimal.ZERO)
                } else {
                    baseCurrencyItem.value.set(BigDecimal(s.toString()))
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrencyItemViewBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private inner class OnMapChangedCallback :
        ObservableMap.OnMapChangedCallback<ObservableMap<String, CurrencyItem>, String, CurrencyItem>() {
        override fun onMapChanged(sender: ObservableMap<String, CurrencyItem>?, key: String?) {
            items.clear()
            val baseCurrency = baseCurrencyItem.currency.get()
            sender?.forEach {

                if (it.key == baseCurrency) {
                    items.add(0, it.value)
                } else {
                    items.add(it.value)
                }
            }
            notifyDataSetChanged()
        }

    }
}