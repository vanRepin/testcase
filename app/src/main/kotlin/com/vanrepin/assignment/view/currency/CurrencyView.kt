package com.vanrepin.assignment.view.currency

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import org.jetbrains.anko.AnkoComponent

interface CurrencyView : AnkoComponent<Context> {
    val imageView: RoundedImageView
    val currencyValueInput: EditText
    val currencyCodeText: TextView
    val currencyNameText: TextView
}