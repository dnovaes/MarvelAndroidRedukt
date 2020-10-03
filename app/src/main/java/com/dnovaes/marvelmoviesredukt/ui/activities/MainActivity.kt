package com.dnovaes.marvelmoviesredukt.ui.activities

import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import com.dnovaes.marvelmoviesredukt.ui.activities.base.StateActivity
import com.dnovaes.marvelmoviesredukt.ui.components.horizontalProgressBar
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.size
import trikita.anvil.BaseDSL.visibility
import trikita.anvil.DSL.indeterminate
import trikita.anvil.DSL.text
import trikita.anvil.DSL.textView

class MainActivity : StateActivity() {

    override fun initialState() {
        ActionCreator.instance.fetchMovies(SAGA)
    }

    override fun content() {
        val state = MarvelMoviesApplication.redukt.state

        horizontalProgressBar {
            size(MATCH, this.dp(R.dimen.sync_bar))
            indeterminate(true)
            visibility(state.syncRunning)
        }

        textView {
            size(MATCH, MATCH)
            text("Number of movies: ${state.movies.values.count()}")
        }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.syncRunning != oldState.syncRunning
    }

    override fun onChanged(state: AppState) {
        layout?.render()
    }
}