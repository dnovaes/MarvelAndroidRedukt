package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.text.TextUtils
import android.view.Gravity
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.color
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.utils.Font.fontWeight
import com.dnovaes.marvelmoviesredukt.utils.FontWeight
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.alignParentLeft
import trikita.anvil.BaseDSL.alignParentRight
import trikita.anvil.BaseDSL.centerVertical
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.text
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.BaseDSL.toLeftOf
import trikita.anvil.BaseDSL.toRightOf
import trikita.anvil.BaseDSL.visibility
import trikita.anvil.DSL.backgroundColor
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.backgroundTintList
import trikita.anvil.DSL.ellipsize
import trikita.anvil.DSL.gravity
import trikita.anvil.DSL.id
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.maxLines
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.relativeLayout
import trikita.anvil.DSL.textColor
import trikita.anvil.DSL.textView
import trikita.anvil.DSL.view

inline fun topActionBar(crossinline func: TopActionBar.() -> Unit) {
    highOrderComponent(func)
}

class TopActionBar(context: Context) : LinearLayoutComponent(context) {

    private var title: String? = null
    private var leftIcon: Int? = null
    private var rightIcon: Int? = null
    private var background: Int = R.drawable.rounded_border_badge
    private var onClickLeftIcon: (() -> Unit)? = null
    private var onClickRightIcon: (() -> Unit)? = null

    companion object {
        val leftIconId = generateViewId()
        val rightIconId = generateViewId()
    }

    override fun view() {
        backgroundResource(background)
        orientation(VERTICAL)

        renderBarContent()
        renderBottomDivider()
    }

    private fun renderBarContent() {
        relativeLayout {
            size(MATCH, WRAP)
            margin(context.dp(R.dimen.margin_medium), context.dp(R.dimen.margin_default))

            renderLeftIcon()
            renderTitle()
            renderRightIcon()
        }
    }

    private fun renderIcon(properties: () -> Unit) {
        imageView {
            properties.invoke()
            size(context.dp(R.dimen.icon_small), context.dp(R.dimen.icon_small))
            centerVertical()
            backgroundTintList(ColorStateList.valueOf(context.color(R.color.colorPrimary)))
        }
    }

    private fun renderLeftIcon() {
        renderIcon {
            id(leftIconId)
            alignParentLeft()
            visibility(leftIcon != null)
            leftIcon?.let { backgroundResource(it) }

            onClickInit {
                onClickLeftIcon?.invoke()
            }
        }
    }

    private fun renderRightIcon() {
        renderIcon {
            id(rightIconId)
            alignParentRight()
            visibility(rightIcon != null)
            rightIcon?.let { backgroundResource(it) }

            onClickInit {
                onClickRightIcon?.invoke()
            }
        }
    }

    private fun renderTitle() {
        textView {
            size(WRAP, WRAP)
            text(title)
            textSize(context.sp(R.dimen.title_size))
            textColor(context.color(R.color.colorPrimary))
            fontWeight(context, FontWeight.W700)
            gravity(Gravity.CENTER)
            if (leftIcon != null)
                toRightOf(leftIconId)
            else
                alignParentLeft()
            if (rightIcon != null)
                toLeftOf(rightIconId)
            else
                alignParentRight()
            ellipsize(TextUtils.TruncateAt.END)
            maxLines(3)
        }
    }

    private fun renderBottomDivider() {
        view {
            size(MATCH, context.dp(R.dimen.top_bar_divider))
            backgroundColor(context.color(R.color.colorPrimary))
        }
    }

    fun title(title: String) {
        if (this.title == title) return
        this.title = title
        hasChanged = true
    }

    fun leftIcon(icon: Int?) {
        if (this.leftIcon == icon) return
        this.leftIcon = icon
        hasChanged = true
    }

    fun onClickLeftIcon(callback: ()-> Unit) {
        this.onClickLeftIcon = callback
    }

    fun rightIcon(icon: Int?) {
        if (this.rightIcon == icon) return
        this.rightIcon = icon
        hasChanged = true
    }

    fun onClickRightIcon(callback: ()-> Unit) {
        this.onClickRightIcon = callback
    }
}
