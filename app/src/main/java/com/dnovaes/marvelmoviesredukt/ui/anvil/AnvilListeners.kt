package com.dnovaes.marvelmoviesredukt.ui.anvil

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import trikita.anvil.Anvil
import trikita.anvil.BaseDSL.init
import java.util.Timer
import java.util.TimerTask

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

inline fun onTextChanged(crossinline func: (text: String) -> Unit) {
    val view: TextView = Anvil.currentView()
    var oldValue = view.text.toString()
    var timer: Timer? = null

    view.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            timer = Timer()
            timer?.schedule(object : TimerTask() {
                override fun run() {
                    if (oldValue != view.text.toString())
                        func.invoke(view.text.toString())
                    timer = null
                }
            }, 200)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            oldValue = view.text.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            timer?.cancel()
        }
    })
}

inline fun onTextChangedInit(crossinline func: (text: String) -> Unit) {
    init { onTextChanged { func.invoke(it) } }
}
