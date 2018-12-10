package com.vanrepin.assignment.controller.impl

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.vanrepin.assignment.controller.CurrencyController
import org.jetbrains.anko.AnkoContext

open class CurrencyViewHolder(
    open val controller: CurrencyController,
    context: Context,
    onSelect: (Int) -> Unit
) :
    RecyclerView.ViewHolder(controller.view.createView(AnkoContext.create(context))) {

    init {
        itemView.setOnClickListener { onSelect(adapterPosition) }
        controller.view.currencyValueInput.apply {
            isFocusable = false
            setOnClickListener { onSelect(adapterPosition) }
        }
    }
}