package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.content.res.ColorStateList
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.color
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.utils.Font.fontWeight
import com.dnovaes.marvelmoviesredukt.utils.FontWeight
import com.dnovaes.marvelmoviesredukt.utils.GlideHelper.glideBitmap
import trikita.anvil.Anvil.currentView
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.alignParentLeft
import trikita.anvil.BaseDSL.alignParentRight
import trikita.anvil.BaseDSL.centerInParent
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.text
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.BaseDSL.toLeftOf
import trikita.anvil.BaseDSL.toRightOf
import trikita.anvil.BaseDSL.weight
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.backgroundTintList
import trikita.anvil.DSL.id
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.linearLayout
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.relativeLayout
import trikita.anvil.DSL.textView
import trikita.anvil.DSL.view
import trikita.anvil.DSL.weightSum

inline fun movieView(crossinline func: MovieView.() -> Unit) {
    highOrderComponent(func)
}

class MovieView(context: Context): LinearLayoutComponent(context) {

    private var movie: Movie? = null
    private var onClickBack: (() -> Unit)? = null

    companion object {
        val ratedBadgeId = generateViewId()
        val releasedBadgeId = generateViewId()
        val dividerId = generateViewId()
    }

    override fun view() {
        val movie = movie ?: return
        size(MATCH, MATCH)
        orientation(VERTICAL)
        padding(0, 0, 0, context.dp(R.dimen.padding_default))

        renderMoviePoster(movie)
        renderMovieInfo(movie)
    }

    private fun renderMovieInfo(movie: Movie) {
        renderHighlightedInfo(movie)
        renderCompleteInfo(movie)
    }

    private fun renderHighlightedInfo(movie: Movie) {
        relativeLayout {
            size(MATCH, WRAP)
            margin(context.dp(R.dimen.margin_default), 0)

            extendableBadge {
                id(releasedBadgeId)
                toLeftOf(dividerId)
                alignParentLeft()
                movieInfoBadgeParams(this)
                label(context.getString(R.string.genre))
                value(movie.genre)
                widthTextView(MATCH)
                renderIfChanged()
            }

            view {
                id(dividerId)
                size(context.dp(R.dimen.one_dip), WRAP)
                centerInParent()
            }

            extendableBadge {
                id(ratedBadgeId)
                toRightOf(dividerId)
                alignParentRight()
                movieInfoBadgeParams(this)
                label(context.getString(R.string.released))
                value(movie.released)
                widthTextView(MATCH)
                renderIfChanged()
            }
        }
    }

    private fun movieInfoBadgeParams(view: ExtendableBadge) {
        view.background(R.drawable.background_dashed)
        backgroundTintList(ColorStateList.valueOf(context.color(R.color.colorPrimary)))
        view.fontColor(context.color(R.color.white))
        view.textSizeOfBadge(context.sp(R.dimen.subtitle_text_size))
        margin(context.dp(R.dimen.margin_default))
    }

    private fun renderCompleteInfo(movie: Movie) {
        linearLayout {
            size(MATCH, WRAP)
            orientation(VERTICAL)
            margin(context.dp(R.dimen.margin_default), 0)

            linearLayout {
                size(MATCH, WRAP)
                weightSum(1f)
                orientation(HORIZONTAL)
                renderDescriptionField(formatTitle(context.getString(R.string.rated), movie.rated), .5f)
                renderDescriptionField(formatTitle(context.getString(R.string.runtime), movie.runtime), .5f)
            }
            renderDescriptionField(formatTitle(context.getString(R.string.director), movie.director))
            renderDescriptionField(formatTitle(context.getString(R.string.writer), movie.writer))
            renderDescriptionField(formatTitle(context.getString(R.string.actors), movie.actors))
            renderDescriptionField(formatTitle(context.getString(R.string.plot), movie.plot))
        }
    }

    private fun formatTitle(label: String, content: String): SpannableString {
        val spanText = SpannableString("$label: $content")
        spanText.setSpan(ForegroundColorSpan(context.color(R.color.colorPrimary)), 0, label.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spanText
    }

    private fun renderDescriptionField(description: SpannableString, weight: Float? = null) {
        textView {
            weight?.let {
                size(0, WRAP)
                weight(it)
            } ?: size(MATCH, WRAP)
            margin(context.dp(R.dimen.margin_default))
            padding(context.dp(R.dimen.padding_default))
            fontWeight(context, FontWeight.W500)
            text(description)
            textSize(context.sp(R.dimen.subtitle_text_size))
            backgroundResource(R.drawable.background_dashed)
        }
    }

    private fun renderMoviePoster(movie: Movie) {
        imageView {
            size(MATCH, WRAP)
            glideBitmap(context, movie.poster, currentView())

            onClickInit {
                onClickBack?.invoke()
            }
        }
    }

    fun movie(movie: Movie) {
        if (this.movie == movie) return
        this.movie = movie
        hasChanged = true
    }

    fun onClickBack(callback: ()-> Unit) {
        this.onClickBack = callback
    }
}
