package com.dnovaes.marvelmoviesredukt.ui.components

import android.content.Context
import android.text.InputType
import android.view.Gravity.CENTER_VERTICAL
import android.widget.EditText
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.extensions.color
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.ui.anvil.LinearLayoutComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.ReactiveLinearComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.highOrderComponent
import com.dnovaes.marvelmoviesredukt.ui.anvil.onClickInit
import com.dnovaes.marvelmoviesredukt.ui.anvil.onTextChangedInit
import trikita.anvil.Anvil.currentView
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.margin
import trikita.anvil.BaseDSL.padding
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.backgroundColor
import trikita.anvil.DSL.backgroundResource
import trikita.anvil.DSL.editText
import trikita.anvil.DSL.gravity
import trikita.anvil.DSL.imageView
import trikita.anvil.DSL.inputType
import trikita.anvil.DSL.orientation
import trikita.anvil.DSL.selectAllOnFocus
import trikita.anvil.DSL.textColor

inline fun movieScoreView(crossinline func: MovieScoreView.() -> Unit) {
    highOrderComponent(func)
}

class MovieScoreView(context: Context): ReactiveLinearComponent(context) {

    private var movieId: Int = 0
    private var score: Double = 0.0
    private var onClickStar: ((Double) -> Unit)? = null

    override fun view() {
        size(MATCH, context.dp(R.dimen.movie_score_view_height))
        orientation(HORIZONTAL)
        margin(context.dp(R.dimen.margin_xx_default), context.dp(R.dimen.margin_default))

        imageView {
            size(context.dp(R.dimen.icon_small), context.dp(R.dimen.icon_small))
            margin(context.dp(R.dimen.margin_small), context.dp(R.dimen.margin_default))
            if (score == 0.0)
                backgroundResource(R.drawable.ic_star_unfilled_24)
            else
                backgroundResource(R.drawable.ic_star_filled_24)
            onClickInit {
                onClickStar?.invoke(score)
                clearFocus()
            }
        }

        editText {
            size(MATCH, MATCH)
            inputType(InputType.TYPE_NUMBER_FLAG_DECIMAL+ InputType.TYPE_CLASS_NUMBER)
            currentView<EditText>().setText(score.toString())
            selectAllOnFocus(true)
            padding(0, context.dp(R.dimen.one_dip), 0, 0)
            textColor(context.color(R.color.colorPrimary))
            backgroundColor(android.R.color.transparent)
            gravity(CENTER_VERTICAL)
            onTextChangedInit {
                if (it.isEmpty()) return@onTextChangedInit
                score = it.toDouble()
            }
        }
    }

    fun movieId(movieId: Int) {
        this.movieId = movieId
        hasChanged = true
    }

    fun score(score: Double) {
        this.score = score
        hasChanged = true
    }

    fun onClickStar(callback: (Double) -> Unit) {
        this.onClickStar = callback
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        val oldMovie = oldState.movies.values.firstOrNull { it._id.toInt() == movieId } ?: return false
        return oldMovie.score != score
    }

    override fun onChanged(state: AppState) {
        render()
    }
}
