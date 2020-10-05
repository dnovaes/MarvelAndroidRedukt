package com.dnovaes.marvelmoviesredukt.ui.components.base

import android.content.Context
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.extensions.sp
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.utils.Font.fontWeight
import com.dnovaes.marvelmoviesredukt.utils.FontWeight
import trikita.anvil.BaseDSL.CENTER
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.textSize
import trikita.anvil.DSL.gravity
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.text
import trikita.anvil.DSL.textView
import trikita.anvil.DSL.visibility

abstract class MoviesFeedLayout(context: Context): LinearLayoutComponent(context) {

    protected var movies: List<Movie> = emptyList()
    protected var onClickMovie: ((Int) -> Unit)? = null

    override fun view() {
        size(MATCH, MATCH)
        orientation(VERTICAL)
        padding(0, context.dp(R.dimen.padding_default))

        textView {
            size(MATCH, WRAP)
            text(context.getString(R.string.no_movies_found))
            textSize(context.sp(R.dimen.subheading_text_size))
            visibility(movies.isEmpty())
            fontWeight(this.context, FontWeight.W400)
            gravity(CENTER)
            margin(0, context.dp(R.dimen.padding_default))
        }

        if (movies.isNotEmpty()) {
            for (i in 0 until movies.count()) {
                renderCard(i)
            }
        }
    }

    abstract fun renderCard(linePos: Int)

    fun movies(movies: List<Movie>) {
        this.movies = movies
        hasChanged = true
    }

    fun onClickMovie(callback: (Int)-> Unit) {
        this.onClickMovie = callback
    }
}
