package com.vanrepin.assignment.view.currency

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.makeramen.roundedimageview.RoundedImageView
import com.vanrepin.assignment.R
import com.vanrepin.assignment.view.roundedImageView
import org.jetbrains.anko.*

class CurrencyViewImpl : CurrencyView {

    override lateinit var imageView: RoundedImageView
        private set
    override lateinit var currencyValueInput: EditText
        private set
    override lateinit var currencyCodeText: TextView
        private set
    override lateinit var currencyNameText: TextView
        private set

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        frameLayout {
            lparams(width = matchParent, height = dip(60))

            imageView = roundedImageView {}
                .lparams(width = dip(40), height = dip(40)) {
                    gravity = Gravity.START or Gravity.CENTER_VERTICAL
                }

            currencyCodeText = themedTextView(R.style.CurrencyCodeText)
                .lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.START or Gravity.TOP
                    marginStart = dip(44)
                    topMargin = dip(10)
                }

            currencyNameText = themedTextView(R.style.CurrencyNameText)
                .lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.START or Gravity.BOTTOM
                    marginStart = dip(44)
                    bottomMargin = dip(10)
                }
            currencyValueInput = themedEditText(R.style.CurrencyValueText) {
                singleLine = true
                imeOptions = EditorInfo.IME_ACTION_DONE
            }.lparams(width = wrapContent, height = wrapContent) {
                gravity = Gravity.END or Gravity.CENTER_VERTICAL
            }
        }
    }
}