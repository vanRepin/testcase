package com.vanrepin.assignment.controller

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.vanrepin.assignment.controller.impl.CurrencyAdapter
import com.vanrepin.assignment.controller.impl.CurrencyViewHolder
import com.vanrepin.assignment.model.CurrencyCode
import com.vanrepin.assignment.model.CurrencyRegistry
import com.vanrepin.assignment.view.mockCurrencyView
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(manifest= Config.NONE)
@RunWith(value = RobolectricTestRunner::class)
class CurrencyAdapterTest {

    private lateinit var out: CurrencyAdapter

    private lateinit var registry: CurrencyRegistry

    private lateinit var baseCurrencyController: CurrencyController

    private lateinit var currencyController: CurrencyController
    private lateinit var currencyViewHolder: CurrencyViewHolder
    private lateinit var onSelectCallback: (Int) -> Unit

    private val items = mutableListOf(CurrencyCode("EUR"), CurrencyCode("USD"))


    @Before
    fun setUp() {
        registry = mock {
            on { baseCurrency.value } doReturn "WTF"
        }
        baseCurrencyController = mock { }
        val currencyView = mockCurrencyView { }
        currencyController = mock {
            on { view } doReturn currencyView
        }

        currencyViewHolder = mock {
            on { controller } doReturn currencyController
        }

        out = CurrencyAdapter(items, registry, baseCurrencyController) { _, onSelect ->
            onSelectCallback = onSelect
            currencyViewHolder
        }
    }

    @Test
    fun onCreateViewHolder() {
        val result = out.onCreateViewHolder(mock { on { context } doReturn mock { } }, 0)
        assertEquals(currencyViewHolder, result)

    }

    @Test
    fun getItemCount() {
        assertEquals(2, out.itemCount)
    }

    @Test
    fun onBindViewHolder() {
        val result = out.onCreateViewHolder(mock { on { context } doReturn mock { } }, 0)
        out.onBindViewHolder(result, 0)
        verify(result.controller).update(items[0])

        out.onBindViewHolder(result, 1)
        verify(result.controller).update(items[1])
    }

    @Test
    fun onSelect_should_swap_children() {
        out.onCreateViewHolder(mock { on { context } doReturn mock { } }, 0)
        onSelectCallback(1)
        assertEquals(CurrencyCode("WTF"), items[0])
        assertEquals(CurrencyCode("EUR"), items[1])

        verify(registry).baseCurrency = CurrencyCode("USD")
    }
}