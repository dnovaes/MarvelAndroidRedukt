package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.ui.components.base.MoviesFeedLayout
import com.dnovaes.marvelmoviesredukt.utils.GlideHelper.glideBitmap
import trikita.anvil.Anvil.currentView
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.linearLayout
import trikita.anvil.DSL.orientation

inline fun moviesFeed(crossinline func: MoviesFeed.() -> Unit) {
    highOrderComponent(func)
}

class MoviesFeed(context: Context): MoviesFeedLayout(context) {

    override fun renderCard(linePos: Int) {
        linearLayout {
            size(MATCH, WRAP)
            orientation(HORIZONTAL)
            margin(context.dp(R.dimen.margin_medium), context.dp(R.dimen.margin_default))
            backgroundResource(R.drawable.background_dashed)
            padding(context.dp(R.dimen.padding_default))

            renderPoster(linePos)
            renderMovieInfo(linePos)

            onClickInit {
                onClickMovie?.invoke(linePos)
            }
        }
    }

    private fun renderPoster(linePos: Int) {
        imageView {
            size(WRAP, context.dp(R.dimen.movie_feed_element_image_height))
            glideBitmap(context, movies[linePos].poster, currentView())
        }
    }

    private fun renderMovieInfo(linePos: Int) {
        val movie = movies[linePos]
        val badgesDescription = mapOf(
            context.getString(R.string.released) to movie.released,
            context.getString(R.string.genre) to movie.genre
        )

        cardViewBadges {
            size(MATCH, WRAP)
            badgeTitle(movie.title)
            badgesContent(badgesDescription)
            renderIfChanged()
        }
    }
}
