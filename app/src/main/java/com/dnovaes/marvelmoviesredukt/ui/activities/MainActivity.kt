package com.dnovaes.marvelmoviesredukt.ui.activities

import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.R
import com.dnovaes.marvelmoviesredukt.actions.ActionCreator
import com.dnovaes.marvelmoviesredukt.extensions.dp
import com.dnovaes.marvelmoviesredukt.models.AppState
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.services.RouteConstants.SAGA
import com.dnovaes.marvelmoviesredukt.ui.activities.base.StateActivity
import com.dnovaes.marvelmoviesredukt.ui.components.TopActionBar
import com.dnovaes.marvelmoviesredukt.ui.components.horizontalProgressBar
import com.dnovaes.marvelmoviesredukt.ui.components.movieView
import com.dnovaes.marvelmoviesredukt.ui.components.moviesFeed
import com.dnovaes.marvelmoviesredukt.ui.components.searchField
import com.dnovaes.marvelmoviesredukt.ui.components.topActionBar
import trikita.anvil.BaseDSL.MATCH
import trikita.anvil.BaseDSL.WRAP
import trikita.anvil.BaseDSL.size
import trikita.anvil.DSL.indeterminate
import trikita.anvil.DSL.scrollView
import trikita.anvil.DSL.visibility

class MainActivity : StateActivity() {

    private var selectedMovie: Movie? = null
    private var searchMode: Boolean = false

    override fun initialState() {
        ActionCreator.instance.fetchMovies(SAGA)
    }

    override fun buildActionBar() {
        topActionBar {
            size(MATCH, WRAP)
            buildTopBarContent(this)
            onClickLeftIcon {
                if (selectedMovie == null) return@onClickLeftIcon
                selectedMovie = null
                layout?.render()
            }
            onClickRightIcon {
                searchMode = !searchMode
                layout?.render()
            }
            renderIfChanged()
        }

        searchField {
            size(MATCH, WRAP)
            visibility(searchMode && selectedMovie == null)

            onFinishTyping {
                ActionCreator.instance.searchMovies(it)
            }

            onClose {
                searchMode = false
                layout?.render()
            }
        }
    }

    private fun buildTopBarContent(view: TopActionBar) {
        selectedMovie?.let {
            view.title("${it.title} (${it.year})")
            view.leftIcon(R.drawable.ic_arrow_back_24)
            view.rightIcon(null)
        } ?: apply {
            view.title(this.getString(R.string.app_name))
            view.leftIcon(R.drawable.ic_thanos_hand_24)
            view.rightIcon(R.drawable.ic_search_icon_24)
        }
    }

    override fun content() {
        val state = MarvelMoviesApplication.redukt.state

        horizontalProgressBar {
            size(MATCH, this.dp(R.dimen.sync_bar))
            indeterminate(true)
            visibility(state.syncRunning)
        }

        scrollView {
            size(MATCH, MATCH)
            visibility(selectedMovie == null)
            moviesFeed {
                movies(state.getFilteredMovies().values.toList())
                onClickMovie {
                    if (state.movies.isEmpty()) return@onClickMovie
                    val movies = state.getFilteredMovies().values.toList()
                    selectedMovie = movies[it]
                    layout?.render()
                }
                renderIfChanged()
            }
        }

        scrollView {
            size(MATCH, MATCH)
            visibility(selectedMovie != null)
            movieView {
                selectedMovie?.let { movie(it) }
                onClickBack {
                    selectedMovie = null
                    layout?.render()
                }
                renderIfChanged()
            }
        }
    }

    override fun hasChanged(newState: AppState, oldState: AppState): Boolean {
        return newState.syncRunning != oldState.syncRunning ||
                newState.getFilteredMovies() != oldState.getFilteredMovies()
    }

    override fun onChanged(state: AppState) {
        layout?.render()
    }
}
