package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.widget.ProgressBar
import trikita.anvil.DSL

inline fun horizontalProgressBar(crossinline func: () -> Unit) {
    DSL.v(HorizontalProgressBar::class.java) {
        func()
    }
}

class HorizontalProgressBar(context: Context):
        ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)