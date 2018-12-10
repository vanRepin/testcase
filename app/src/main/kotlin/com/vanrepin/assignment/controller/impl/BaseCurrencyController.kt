package com.vanrepin.assignment.controller.impl

import android.text.TextWatcher
import com.vanrepin.assignment.controller.CurrencyController
import com.vanrepin.assignment.controller.impl.input.BaseCurrencyInputFilter
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRegistry
import com.vanrepin.assignment.view.InputHelper
import com.vanrepin.assignment.view.currency.CurrencyView
import com.vanrepin.assignment.view.doWithoutNotification
import java.math.BigDecimal


class BaseCurrencyController(
    override val view: CurrencyView,
    private val registry: CurrencyRegistry,
    private val inputHelper: InputHelper,
    private val textWatcher: TextWatcher
) : CurrencyController {

    init {
        view.currencyValueInput.apply {
            filters = arrayOf(
                BaseCurrencyInputFilter(
                    INPUT_MAX_LENGTH,
                    INPUT_MAX_VALUE
                )
            )
            addTextChangedListener(textWatcher)
        }
    }

    override fun update(currency: CurrencyCode) {
        super.update(currency)
        updateValue(registry.getFormattedValue(currency))
    }

    private fun updateValue(formattedValue: String) {
        clampBaseValue()
        view.currencyValueInput.doWithoutNotification(textWatcher) {
            setText(formattedValue)
            setSelection(formattedValue.length)
            inputHelper.showKeyboard(this)
        }
    }

    private fun clampBaseValue() {
        if (registry.baseValue > INPUT_MAX_VALUE) {
            registry.setBaseValue(INPUT_MAX_VALUE, false)
        }
    }

    companion object {
        private const val INPUT_MAX_LENGTH: Int = 12
        private val INPUT_MAX_VALUE: BigDecimal = BigDecimal("1000000")
    }
}