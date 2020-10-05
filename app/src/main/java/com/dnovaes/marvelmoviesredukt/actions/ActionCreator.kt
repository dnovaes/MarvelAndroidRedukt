package com.dnovaes.marvelmoviesredukt.actions

import com.dnovaes.marvelmoviesredukt.MarvelMoviesApplication
import com.dnovaes.marvelmoviesredukt.actions.Actions.SYNC_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.LOAD_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.LOAD_STATE
import com.dnovaes.marvelmoviesredukt.actions.Actions.STATE_LOADED
import com.dnovaes.marvelmoviesredukt.actions.Actions.SEARCH_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.SAVE_SEARCHED_MOVIES
import com.dnovaes.marvelmoviesredukt.actions.Actions.UPDATE_MOVIE_SCORE
import com.dnovaes.marvelmoviesredukt.actions.Actions.UPDATE_SYNC
import com.dnovaes.marvelmoviesredukt.models.Movie
import com.dnovaes.marvelmoviesredukt.models.payloads.MoviesPayload
import com.github.raulccabreu.redukt.actions.Action

class ActionCreator private constructor() {

    private object Holder {
        val INSTANCE = ActionCreator()
    }

    companion object {
        val instance: ActionCreator by lazy { Holder.INSTANCE }
    }

    fun syncMovies(contentType: String) {
        asyncDispatch(Action<Any>(SYNC_MOVIES, contentType))
    }

    fun saveMovies(sagaType: String, content: List<Movie>) {
        asyncDispatch(Action<Any>(SAVE_MOVIES, MoviesPayload(sagaType, content)))
    }

    fun updateSync(status: Boolean) {
        asyncDispatch(Action<Any>(UPDATE_SYNC, status))
    }

    fun searchMovies(filter: String) {
        asyncDispatch(Action<Any>(SEARCH_MOVIES, filter))
    }

    fun saveSearchedMovies(payload: MoviesPayload) {
        asyncDispatch(Action<Any>(SAVE_SEARCHED_MOVIES, payload))
    }

    fun stateLoaded() {
        asyncDispatch(Action<Any>(STATE_LOADED))
    }

    fun loadMovies(payload: MoviesPayload) {
        asyncDispatch(Action<Any>(LOAD_MOVIES, payload))
    }

    fun loadState() {
        asyncDispatch(Action<Any>(LOAD_STATE))
    }

    fun updateMovieScore(movie: Movie) {
        asyncDispatch(Action<Any>(UPDATE_MOVIE_SCORE, movie))
    }

    private fun asyncDispatch(action: Action<*>) {
        MarvelMoviesApplication.redukt.dispatch(action, true)
    }

}
