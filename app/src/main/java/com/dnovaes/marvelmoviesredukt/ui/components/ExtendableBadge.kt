package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.graphics.Color.BLACK
import android.text.TextUtils.TruncateAt.END
import android.view.Gravity
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.utils.Font.fontWeight
import com.dnovaes.marvelmoviesredukt.utils.FontWeight
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.ellipsize
import trikita.anvil.DSL.gravity
import trikita.anvil.DSL.maxLines
import trikita.anvil.DSL.text
import trikita.anvil.DSL.textColor
import trikita.anvil.DSL.textView

inline fun extendableBadge(crossinline func: ExtendableBadge.() -> Unit) {
    highOrderComponent(func)
}

open class ExtendableBadge(context: Context) : LinearLayoutComponent(context) {

    private var label: String? = null
    private var value: String? = null

    private var background: Int = R.drawable.rounded_border_badge
    private var textSize: Float? = null
    private var extendedMode: Boolean = false
    private var fontColor: Int = BLACK
    private var widthTextView: Int = WRAP
    private var maxLines: Int = 1
    private var gravityDefault: Int = Gravity.CENTER

    override fun view() {
        if (extendedMode)
            size(MATCH, WRAP)
        else
            size(WRAP, WRAP)
        backgroundResource(background)
        padding(context.dp(R.dimen.padding_tiny))

        renderTextView()

        onClickInit {
            extendedMode = !extendedMode
            render()
        }
    }

    private fun renderTextView() {
        textView {
            setBadgeParams()
            ellipsize(END)
            padding(context.dp(R.dimen.padding_tiny), 0)
            textSize?.let { textSize(it) }
            fontWeight(context, FontWeight.W500)
            textColor(fontColor)
        }
    }

    private fun setBadgeParams() {
        if (extendedMode) {
            size(MATCH, WRAP)
            text("$label: $value")
            maxLines(3)
            gravity(Gravity.CENTER)
        } else {
            size(widthTextView, WRAP)
            text(value)
            maxLines(maxLines)
            gravity(gravityDefault)
        }
    }

    fun label(label: String) {
        if (this.label == label) return
        this.label = label
        hasChanged = true
    }

    fun value(value: String) {
        if (this.value == value) return
        this.value = value
        hasChanged = true
    }

    fun background(background: Int) {
        this.background = background
    }

    fun textSizeOfBadge(textSize: Float) {
        this.textSize = textSize
    }

    fun fontColor(fontColor: Int) {
        this.fontColor = fontColor
    }

    fun widthTextView(width: Int) {
        if (widthTextView == width) return
        this.widthTextView = width
        hasChanged = true
    }

    fun maxDefaultLines(maxLines: Int) {
        if (this.maxLines == maxLines) return
        this.maxLines = maxLines
        hasChanged = true
    }

    fun gravityDefault(gravity: Int) {
        if (this.gravityDefault == gravity) return
        this.gravityDefault = gravity
        hasChanged = true
    }
}
