package com.vanrepin.assignment.view

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import com.vanrepin.assignment.MainActivity
import com.vanrepin.assignment.controller.impl.CurrencyAdapter
import com.vanrepin.assignment.view.currency.CurrencyView
import com.vanrepin.assignment.view.currency.CurrencyViewImpl
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class MainView : AnkoComponent<MainActivity> {

    val baseCurrencyView: CurrencyView = CurrencyViewImpl()

    private lateinit var recycleView: RecyclerView
    private lateinit var preloader: View

    override fun createView(ui: AnkoContext<MainActivity>): View = with(ui) {
        verticalLayout {
            ankoView({ baseCurrencyView.createView(AnkoContext.create(it, context)) }, 0, {})

            recycleView = recycleView {
                visibility = View.GONE
                layoutManager = VerticalLinearLayoutManager(context)
            }.lparams(width = matchParent, height = matchParent)
            preloader = frameLayout {
                progressBar {
                    isIndeterminate = true
                }.lparams(width = wrapContent, height = wrapContent) {
                    gravity = Gravity.CENTER
                }
            }.lparams(width = matchParent, height = matchParent)
            lparams(width = matchParent, height = matchParent) {
                marginEnd = dip(8)
                marginStart = dip(8)
            }
        }
    }

    fun setAdapter(adapter: CurrencyAdapter) {
        recycleView.adapter = adapter
        recycleView.visibility = View.VISIBLE
        preloader.visibility = View.GONE
    }
}