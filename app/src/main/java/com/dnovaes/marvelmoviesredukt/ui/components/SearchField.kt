package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.content.res.ColorStateList
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.color
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.ui.anvil.RelativeLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.ui.anvil.onTextChangedInit
import com.dnovaes.marvelmoviesredukt.utils.Font.fontWeight
import com.dnovaes.marvelmoviesredukt.utils.FontWeight
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.alignParentRight
import trikita.anvil.BaseDSL.centerVertical
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.text
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.BaseDSL.toLeftOf
import trikita.anvil.DSL.backgroundColor
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.backgroundTintList
import trikita.anvil.DSL.editText
import trikita.anvil.DSL.id
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.textColor

inline fun searchField(crossinline func: SearchField.() -> Unit) {
    highOrderComponent(func)
}

class SearchField(context: Context) : RelativeLayoutComponent(context) {

    private var onFinishTyping: ((String) -> Unit)? = null
    private var onClose: (() -> Unit)? = null
    private var fieldContent: String? = null

    companion object {
        val searchFieldId = generateViewId()
        val searchCloseIconId = generateViewId()
    }

    override fun view() {
        backgroundResource(R.drawable.background_dashed)
        margin(context.dp(R.dimen.margin_medium), context.dp(R.dimen.margin_default))
        padding(context.dp(R.dimen.padding_default), 0)

        editText {
            id(searchFieldId)
            size(MATCH, WRAP)
            toLeftOf(searchCloseIconId)
            text(fieldContent)
            textSize(context.sp(R.dimen.subtitle_text_size))
            textColor(context.color(R.color.colorPrimary))
            backgroundColor(android.R.color.transparent)
            fontWeight(this.context, FontWeight.W500)
            onTextChangedInit {
                if (it.isEmpty()) {
                    fieldContent = null
                    onFinishTyping?.invoke(it)
                } else {
                    fieldContent = it
                    onFinishTyping?.invoke(it)
                }
            }
        }

        imageView {
            id(searchCloseIconId)
            size(WRAP, WRAP)
            alignParentRight()
            centerVertical()
            backgroundResource(R.drawable.ic_cross_24)
            backgroundTintList(ColorStateList.valueOf(context.color(R.color.colorPrimary)))
            onClickInit {
                if (fieldContent == null)
                    onClose?.invoke()
                else {
                    fieldContent = null
                    render()
                }
            }
        }
    }

    fun onFinishTyping(callback: (String)-> Unit) {
        this.onFinishTyping = callback
    }

    fun onClose(callback: ()-> Unit) {
        this.onClose = callback
    }
}
