package com.vanrepin.assignment.controller

import android.text.TextWatcher
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vanrepin.assignment.R
import com.vanrepin.assignment.controller.impl.BaseCurrencyController
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRegistry
import com.vanrepin.assignment.view.InputHelper
import com.vanrepin.assignment.view.currency.CurrencyView
import com.vanrepin.assignment.view.mockCurrencyView
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class BaseCurrencyControllerTest {

    private val CURRENCY_CODE = CurrencyCode("AUD")

    private lateinit var out: BaseCurrencyController

    private lateinit var registry: CurrencyRegistry
    private lateinit var view: CurrencyView
    private lateinit var inputHelper: InputHelper
    private lateinit var watcher: TextWatcher

    @Before
    fun setUp() {
        registry = mock {
            on { baseValue } doReturn BigDecimal("100")
            on { baseCurrency.value } doReturn "AUD"
            on { getFormattedValue(CURRENCY_CODE) } doReturn "100.00"
        }
        view = mockCurrencyView()

        inputHelper = mock { }
        watcher  = mock {  }
    }

    @Test
    fun init() {
        out = BaseCurrencyController(
            view,
            registry,
            inputHelper,
            watcher
        )
        verify(view.currencyValueInput).addTextChangedListener(watcher)
    }

    @Test
    fun update() {
        out = BaseCurrencyController(
            view,
            registry,
            inputHelper,
            watcher
        )
        out.update(CURRENCY_CODE)
        verify(view.currencyCodeText).text = CURRENCY_CODE.value
        verify(view.currencyNameText).setText(R.string.AUD)
        verify(view.imageView).setImageResource(R.drawable.aud)
        verify(view.currencyValueInput).setText("100.00")
    }

    @Test
    fun update_should_clamp_value() {

        registry = mock {
            on { baseValue } doReturn BigDecimal("9999999")
            on { baseCurrency.value } doReturn "AUD"
            on { getFormattedValue(CURRENCY_CODE) } doReturn "anything"
        }

        out = BaseCurrencyController(
            view,
            registry,
            inputHelper,
            watcher
        )

        out.update(CURRENCY_CODE)
        verify(registry).setBaseValue(BigDecimal("1000000"), false)
    }
}