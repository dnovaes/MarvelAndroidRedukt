package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.content.res.ColorStateList
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.color
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.utils.GlideHelper.glideBitmap
import trikita.anvil.Anvil.currentView
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.below
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.toRightOf
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.backgroundTintList
import trikita.anvil.DSL.id
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.relativeLayout

inline fun moviesFeed(crossinline func: MoviesFeed.() -> Unit) {
    highOrderComponent(func)
}

class MoviesFeed(context: Context): LinearLayoutComponent(context) {

    private var movies: List<Movie> = emptyList()
    private var onClickMovie: ((Int) -> Unit)? = null

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
            margin(context.dp(R.dimen.margin_medium), context.dp(R.dimen.margin_default))
            backgroundResource(R.drawable.background_dashed)
            padding(context.dp(R.dimen.padding_default))

            val posterId = generateViewId()
            renderPoster(linePos, posterId)
            renderMovieInfo(linePos, posterId)

            onClickInit {
                onClickMovie?.invoke(linePos)
            }
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

        extendableBadge {
            id(titleId)
            toRightOf(posterId)
            label("Title")
            value(movie.title)

            margin(context.dp(R.dimen.padding_xx_medium), context.dp(R.dimen.margin_default),
                context.dp(R.dimen.padding_xx_medium), 0)
            backgroundTintList(ColorStateList.valueOf(context.color(R.color.colorPrimary)))
            fontColor(context.color(R.color.white))
            textSizeOfBadge(context.sp(R.dimen.subheading_text_size))


            renderIfChanged()
        }

        extendableBadge {
            id(genreId)
            below(titleId)
            applyBadgeParams(this, posterId)
            label("Released")
            value(movie.released)
            renderIfChanged()
        }

        extendableBadge {
            id(releaseDateId)
            below(genreId)
            applyBadgeParams(this, posterId)
            label("Genre")
            value(movie.genre)
            renderIfChanged()
        }
    }

    private fun applyBadgeParams(view: ExtendableBadge, posterId: Int) {
        toRightOf(posterId)
        margin(context.dp(R.dimen.padding_xx_medium), context.dp(R.dimen.margin_default),
            context.dp(R.dimen.padding_xx_medium), 0)
        view.fontColor(context.color(R.color.colorPrimary))
        view.background(R.drawable.background_dashed)
        view.textSizeOfBadge(context.sp(R.dimen.subheading_text_size))
    }

    fun movies(movies: List<Movie>) {
        if (this.movies == movies) return
        this.movies = movies
        hasChanged = true
    }

    fun onClickMovie(callback: (Int)-> Unit) {
        this.onClickMovie = callback
    }
}
