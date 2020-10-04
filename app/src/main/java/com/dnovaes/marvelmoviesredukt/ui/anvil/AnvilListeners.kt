package com.dnovaes.marvelmoviesredukt.ui.anvil

import android.view.View
import android.widget.CheckBox
import trikita.anvil.Anvil
import trikita.anvil.BaseDSL.init

inline fun onClickInit(crossinline func: (v: View) -> Unit) {
    init { onClick { func.invoke(it) } }
}

inline fun onClick(crossinline func: (v: View) -> Unit) {
    val view: View = Anvil.currentView()
    view.setOnClickListener {
        func.invoke(it)
    }
}

inline fun onLongClickInit(crossinline func: (v: View) -> Boolean) {
    init { onLongClick { func.invoke(it) } }
}

inline fun onLongClick(crossinline func: (v: View) -> Boolean) {
    val view: View = Anvil.currentView()
    view.setOnLongClickListener {
        func.invoke(it)
    }
}

inline fun onCheckedChangeInit(crossinline func: (v: Boolean) -> Unit) {
    init { onCheckedChange { func.invoke(it) } }
}

inline fun onCheckedChange(crossinline func: (v: Boolean) -> Unit) {
    val view: CheckBox = Anvil.currentView()
    view.setOnCheckedChangeListener { _, isChecked ->
        func.invoke(isChecked)
    }
}

