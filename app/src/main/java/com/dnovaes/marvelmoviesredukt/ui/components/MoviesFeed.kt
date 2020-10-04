package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.text.TextUtils
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.utils.Font.fontWeight
import com.dnovaes.marvelmoviesredukt.utils.FontWeight
import com.dnovaes.marvelmoviesredukt.utils.GlideHelper.glideBitmap
import trikita.anvil.Anvil.currentView
import trikita.anvil.BaseDSL.CENTER_VERTICAL
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.below
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.text
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.BaseDSL.toRightOf
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.ellipsize
import trikita.anvil.DSL.gravity
import trikita.anvil.DSL.id
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.maxLines
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.relativeLayout
import trikita.anvil.DSL.textColor
import trikita.anvil.DSL.textView

inline fun moviesFeed(crossinline func: MoviesFeed.() -> Unit) {
    highOrderComponent(func)
}

class MoviesFeed(context: Context): LinearLayoutComponent(context) {

    private var movies: List<Movie> = emptyList()

    override fun view() {
        size(MATCH, MATCH)
        orientation(VERTICAL)
        padding(0, context.dp(R.dimen.padding_default))

        if (movies.isEmpty()) return
        for (i in 0 until movies.count()) {
            renderLine(i)
        }
    }

    private fun renderLine(linePos: Int) {
        relativeLayout {
            size(MATCH, WRAP)
            margin(context.dp(R.dimen.margin_default))
            backgroundResource(R.drawable.background_dashed)
            padding(context.dp(R.dimen.padding_default))

            val posterId = generateViewId()
            renderPoster(linePos, posterId)
            renderMovieInfo(linePos, posterId)
        }
    }

    private fun renderPoster(linePos: Int, posterId: Int) {
        imageView {
            id(posterId)
            size(WRAP, context.dp(R.dimen.movie_feed_element_image_height))
            glideBitmap(context, movies[linePos].poster, currentView())
        }
    }

    private fun renderMovieInfo(linePos: Int, posterId: Int) {
        val titleId = generateViewId()
        val genreId = generateViewId()
        val releaseDateId = generateViewId()
        val movie = movies[linePos]

        textView {
            id(titleId)
            size(MATCH, WRAP)
            toRightOf(posterId)
            padding(context.dp(R.dimen.padding_large), 0)
            fontWeight(context, FontWeight.W500)
            textSize(context.sp(R.dimen.title_size))
            gravity(CENTER_VERTICAL)
            text(movie.title)
            ellipsize(TextUtils.TruncateAt.END)
            maxLines(2)
        }

        textView {
            id(genreId)
            below(titleId)
            margin(0, context.dp(R.dimen.margin_default), 0, 0)
            applyDefaultTextParams(posterId)
            text(movies[linePos].released)
        }

        textView {
            id(releaseDateId)
            below(genreId)
            applyDefaultTextParams(posterId)
            text(movies[linePos].genre)
        }
    }

    private fun applyDefaultTextParams(posterId: Int) {
        toRightOf(posterId)
        padding(context.dp(R.dimen.padding_large), 0)
        textColor(R.color.colorSecundary)
        textSize(context.sp(R.dimen.subtitle_text_size))
    }

    fun movies(movies: List<Movie>) {
        if (this.movies == movies) return
        this.movies = movies
        hasChanged = true
    }
}
