package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.view.Gravity
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.color
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import trikita.anvil.BaseDSL.margin
import trikita.anvil.DSL.backgroundTintList
import trikita.anvil.DSL.orientation

inline fun cardViewBadges(crossinline func: CardViewBadges.() -> Unit) {
    highOrderComponent(func)
}

open class CardViewBadges(context: Context) : LinearLayoutComponent(context) {

    private var content: Map<String, String> = mapOf()
    private var title: String = ""

    override fun view() {
        orientation(VERTICAL)

        renderBadgeTitle()

        content.forEach {
            renderBadgeDescription(it.key, it.value)
        }
    }

    private fun renderBadgeTitle() {
        extendableBadge {
            applyBadgeParams(this)
            label(context.getString(R.string.title))
            value(title)
            backgroundTintList(ColorStateList.valueOf(context.color(R.color.colorPrimary)))
            fontColor(context.color(R.color.white))
            renderIfChanged()
        }
    }

    private fun renderBadgeDescription(label: String, content: String) {
        extendableBadge {
            applyBadgeParams(this)
            label(label)
            value(content)
            fontColor(context.color(R.color.colorPrimary))
            background(R.drawable.background_dashed)
            renderIfChanged()
        }
    }

    private fun applyBadgeParams(view: ExtendableBadge) {
        margin(context.dp(R.dimen.padding_xx_default), context.dp(R.dimen.margin_default),
            context.dp(R.dimen.padding_xx_default), 0)
        view.textSizeOfBadge(context.sp(R.dimen.subheading_text_size))
        view.maxDefaultLines(3)
        view.gravityDefault(Gravity.START)
    }

    fun badgeTitle(title: String) {
        if (this.title == title) return
        this.title = title
        hasChanged = true
    }

    fun badgesContent(content: Map<String, String>) {
        if (this.content == content) return
        this.content = content
        hasChanged = true
    }
}
