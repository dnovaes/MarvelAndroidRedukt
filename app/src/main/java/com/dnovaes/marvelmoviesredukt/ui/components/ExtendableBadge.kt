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
import trikita.anvil.DSL.maxWidth
import trikita.anvil.DSL.text
import trikita.anvil.DSL.textColor
import trikita.anvil.DSL.textView

inline fun extendableBadge(crossinline func: ExtendableBadge.() -> Unit) {
    highOrderComponent(func)
}

open class ExtendableBadge(context: Context) : LinearLayoutComponent(context) {

    protected open var icon: Int? = null
    private var label: String? = null
    var value: String? = null

    protected open var background: Int = R.drawable.rounded_border_badge
    private var textSize: Float? = null
    private var maxWidth: Int = context.dp(R.dimen.badge_max_width)
    private var extendedMode: Boolean = false
    private var fontColor: Int = BLACK

    override fun view() {
        size(WRAP, WRAP)
        backgroundResource(background)
        padding(context.dp(R.dimen.padding_tiny))
        maxWidth(maxWidth)

/*
        imageView {
            visibility(icon != null)
            size(context.dp(R.dimen.icon_small), context.dp(icon_small))
            icon?.let { backgroundResource(it) }
            backgroundTintList(ColorStateList.valueOf(BLACK))
        }
*/

        renderTextView()

        onClickInit {
            extendedMode = !extendedMode
            render()
        }
    }

    private fun renderTextView() {
        textView {
            size(MATCH, MATCH)
            padding(context.dp(R.dimen.padding_tiny), 0)
            if (extendedMode)
                text("$label: $value")
            else
                text(value)
            textSize?.let { textSize(it) }
            fontWeight(context, FontWeight.W500)
            textColor(fontColor)
            gravity(Gravity.CENTER)
            ellipsize(END)
            maxLines(1)
        }
    }

    fun icon(icon: Int?) {
        if (this.icon == icon) return
        this.icon = icon
        render()
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
}
