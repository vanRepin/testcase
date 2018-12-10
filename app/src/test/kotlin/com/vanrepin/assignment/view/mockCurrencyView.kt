package com.vanrepin.assignment.view

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.nhaarman.mockitokotlin2.KStubbing
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.vanrepin.assignment.view.currency.CurrencyView

fun mockCurrencyView(
    currencyCodeTextStubbing: KStubbing<TextView>.() -> Unit = {},
    currencyNameTextStubbing: KStubbing<TextView>.() -> Unit = {},
    currencyValueInputStubbing: KStubbing<EditText>.() -> Unit = {},
    imageViewStubbing: KStubbing<ImageView>.() -> Unit = {}
): CurrencyView {
    return mock {
        on { currencyCodeText } doReturn mock { currencyCodeTextStubbing(this) }
        on { currencyNameText } doReturn mock { currencyNameTextStubbing(this) }
        on { currencyValueInput } doReturn mock { currencyValueInputStubbing(this) }
        on { imageView } doReturn mock { imageViewStubbing(this) }
        on {createView(any())} doReturn mock {}
    }
}